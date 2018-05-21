@       Descricao: Uoli's OS
@ 
@       Autor: Gabriel Pellegrino da Silva (gpsunicamp016@gmail.com)
@ 
@       Data: 2017/2S

@ Comecaremos com o vetor de interrupcoes na primeira posicao de memoria
.org 0x0

.section .iv, "a"

_start:

interrupt_vector:
    b RESET_HANDLER 
    b UNDEFINED_INSTRUCTION_HANDLER
.org 0x08
    b SUPERVISOR_HANDLER
    b ABORT_INSTRUCT_HANDLER
    b ABORD_DATA_HANDLER
.org 0x18
    b IRQ_HANDLER
    b FIQ_HANDLER

.org 0x100
.text


@       RESET_HANDLER eh responsavel por iniciar as variaveis do siste-
@       ma e garantir a sua execucao.
@       Executado apenas no inicio e no termino do programa.
   
     @ Constantes utilizadas pelo programa
    .set MAX_CALLBACKS,         0x8
    .set MAX_ALARMS,            0x8
    .set TIME_SZ,               0x64
    .set LOOP_WAIT,             0x5000 
   
    @ Unico modo que eu precisei dar set 
    .set IRQ_NO_INTERRUP,       0xD2 

    @ Constantes utilizadas para as structs
    .set ALARM_STRUCT,          0x8
    .set CALLBACK_STRUCT,       0xC
    
     @ Enderecos utilizados de GPT, nao eh o professor Guilherme
    .set GPT_BASE,              0x53FA0000
    .set GPT_CR,                0x0 
    .set GPT_PR,                0x4
    .set GPT_SR,                0x8
    .set GPT_IR,                0xC
    .set GPT_OCR1,              0x10

    @ Constantes utilizadas pelo TZIC
    .set TZIC_BASE,             0x0FFFC000
    .set TZIC_INTCTRL,          0x0
    .set TZIC_INTSEC1,          0x84 
    .set TZIC_ENSET1,           0x104
    .set TZIC_PRIOMASK,         0xC
    .set TZIC_PRIORITY9,        0x424

    @ Configuracao do GPIO
    .set GPIO_CONFIG,            0xFFFC003E
    .set GPIO_BASE,              0x53F84000  
    .set GPIO_DR,                0x0
    .set GPIO_DIR,               0x4
    .set GPIO_PSR,               0x8

RESET_HANDLER:
    msr CPSR_c, 0x13
    ldr sp, =SUPERVISOR_STACK_BEGIN

    msr CPSR_c, 0x12
    ldr sp, =IRQ_STACK_BEGIN

    msr CPSR_c, 0x13

@ Zera o processador IRQ
    ldr r2, =CALLBACK_PROCESS
    mov r0, #0
    str r0, [r2]

@ Zera o contador
    ldr r2, =INTERN_TIMER
    str r0, [r2]

@ Zera o numero de Alarmes
    ldr r2, =NUM_OF_ALARMS
    str r0, [r2]

@ Zera o numero de Callbacks
    ldr r2, =NUM_OF_CALLBACKS
    str r0, [r2]

    ldr r0, =interrupt_vector
    mcr p15, 0, r0, c12, c0, 0

    @ Configurando GPT
SET_GPT:
    ldr r1, =GPT_BASE
    mov r0, #0x41
    str r0, [r1, #GPT_CR]
    mov r0, #0
    str r0, [r1, #GPT_PR]           

    mov r0, #1
    str r0, [r1, #GPT_IR]

    ldr r0, =TIME_SZ 
    str r0, [r1, #GPT_OCR1]
    
@ Configurando TZIC 
SET_TZIC:
    @ Constantes para os enderecos do TZIC
    @ Liga o controlador de interrupcoes
    @ R1 <= TZIC_BASE

    ldr	r1, =TZIC_BASE

    @ Configura interrupcao 39 do GPT como nao segura
    mov	r0, #(1 << 7)
    str	r0, [r1, #TZIC_INTSEC1]

    @ Habilita interrupcao 39 (GPT)
    @ reg1 bit 7 (gpt)

    mov	r0, #(1 << 7)
    str	r0, [r1, #TZIC_ENSET1]

    @ Configure interrupt39 priority as 1
    @ reg9, byte 3

    ldr r0, [r1, #TZIC_PRIORITY9]
    bic r0, r0, #0xFF000000
    mov r2, #1
    orr r0, r0, r2, lsl #24
    str r0, [r1, #TZIC_PRIORITY9]

    @ Configure PRIOMASK as 0
    eor r0, r0, r0
    str r0, [r1, #TZIC_PRIOMASK]

    @ Habilita o controlador de interrupcoes
    mov	r0, #1
    str	r0, [r1, #TZIC_INTCTRL]

SET_GPIO:
    ldr r0, =GPIO_BASE
    ldr r1, =GPIO_CONFIG 

    str r1, [r0, #GPIO_DIR]

INIC_ALARMS_CALLBACKS:
    ldr r0, =ALARM_ARRAY
    ldr r3, =CALLBACKS_ARRAY
    mov r1, #-1  
    mov r2, #0    

inic_alarms_callbacks_loop: 
    cmp r2, #MAX_ALARMS
    beq inic_alarms_callbacks_end
   
    @ Coloca -1 nos primeiros 4 bytes de cada alarme e callback 
    str r1, [r0], #ALARM_STRUCT
    str r1, [r3], #CALLBACK_STRUCT
    add r2, r2, #1
    b inic_alarms_callbacks_loop 

inic_alarms_callbacks_end:
    msr CPSR_c, 0x10
    ldr sp, =USER_STACK_BEGIN

    ldr pc, =0x77812000

@ A cada iteracao (chamada IRQ) do GPT, tenho que verificar os alarmes e call-
@ backs. O GPT funciona como um clock interno para me lembrar de fazer as coi-
@ sas. 
IRQ_HANDLER:
    push {r0-r12, lr}           @ Salva os registradores na pilha IRQ
    mrs r12, spsr               @ Salva o modo para evitar perde-lo para outra
                                @ mudanca do modo de operacao do processador

    push {r12}                  @ Empilha o modo
   
    
    mov r1, #1
    ldr r0, =GPT_BASE
    str r1, [r0, #GPT_SR]
    
    ldr r0, =NUM_OF_ALARMS 
    ldr r6, [r0] 

    cmp r1, #0                @ Verifica se ha algum alarme a ser verificado
    beq treat_callbacks_irq   @ Se nao ha alarmes, va cuidar das callbacks
   
@ Precisamos checar todos os alarmes atras de algum
    ldr r9, =ALARM_ARRAY
    ldr r4, =INTERN_TIMER
    ldr r4, [r4]
    mov r5, #0
check_alarms_loop:
    cmp r5, #MAX_ALARMS
    beq treat_callbacks_irq

    @ Pega o tempo do r1-esimo alarme
    ldr r7, [r9], #ALARM_STRUCT

    cmp r7, r4                @ Compara o tempo do alarme atual com o tempo do 
                              @ sistema
    bleq treat_alarm

    add r5, r5, #1
    b check_alarms_loop

treat_alarm:
    push {r4-r12, lr}
    @ Removendo um alarme tratado
    ldr r4, =NUM_OF_ALARMS  
    ldr r5, [r4]
    sub r5, r5, #1
    str r5, [r4]

    @ Volta para a posicao correta 
    sub r10, r9, #ALARM_STRUCT 

    @ Remove o alarme do array
    mov r8, #-1
    str r8, [r10]
    ldr r8, [r10, #4]         @ Pega o endereco que deve ser saltado 
  
    msr CPSR_c, 0x10         @ User mode com IRQ e FIQ enabled
    
    blx r8

@ Depois de voltarmos, precisamos retornar ao modo correto 
    mov r7, #23
    svc 0x0

treat_callbacks_irq:
    ldr r0, =NUM_OF_CALLBACKS
    ldr r5, [r0] 

    cmp r5, #0
    bne treat_callbacks

reset_irq_process:
@ Zerando a flag de que CALLBACKS esta sendo tratado  
    ldr r1, =CALLBACK_PROCESS
    mov r0, #0
    str r0, [r1]
    b irq_end

treat_callbacks:
    ldr r0, =CALLBACK_PROCESS
    ldr r1, [r0]
    cmp r1, #1                @ Verifica se nao estou tratando CALLBACKS ja
    beq irq_end 

    mov r1, #1
    str r1, [r0]
    ldr r4, =CALLBACKS_ARRAY 

    ldr r5, =MAX_CALLBACKS
treat_callbacks_loop:
    cmp r5, #0
    beq reset_irq_process 

    ldr r8, [r4], #4          @ r0: contem id do sonar da callback
    ldr r9, [r4], #4          @ r1: contem o limiar de distancia
    ldr r10, [r4], #4         @ r2: contem a funcao a ser chamada
  
    cmp r8, #-1 
    beq not_triggered
    
    msr CPSR_c, 0x1F          @ Modo System sem interrupcoes

    mov r0, r8
    push {r0}                 @ Colocando na pilha de usuario o id do sonar
    mov r7, #16
    svc 0x0

    msr CPSR_c, 0x1F          @ Modo System sem interrupcoes, para podermos
                              @ remover o parametro empilhado

    pop {r8}

    msr CPSR_c, 0x12          @ Modo IRQ sem interrupcoes 

    cmp r0, r9                @ Comparando a distancia obtida com o limiar
    bgt not_triggered           
  
@ Caso haja uma callback triggada, diminui em um o numero de callbacks a serem
@ tratadas. 
    ldr r0, =NUM_OF_CALLBACKS
    ldr r1, [r0]
    sub r1, r1, #1
    str r1, [r0]

    sub r0, r4, #CALLBACK_STRUCT
    
    mov r1, #-1
    str r1, [r0]
    
    msr CPSR_c, 0x10           @ User mode com FIQ e IRQ enabled 

    blx r10 

    @ Depois de saltar pro codigo de usario, temos de retornar ao modo
    @ anterior
    mov r7, #24 
    svc 0x0

callback_return:
    msr CPSR_c, 0x12           @ IRQ sem interrupcoes

not_triggered: 
    sub r5, r5, #1    
    b treat_callbacks_loop  
     
irq_end:
    ldr r1, =INTERN_TIMER     @ Pega o endereco do contador
    ldr r2, [r1]              @ Pega o valor do contador 
    add r2, r2, #1            @ Soma um no valor
    str r2, [r1]              @ Guarda o valor atualizado

    pop {r12}
    
    msr spsr, r12

    pop {r0-r12, lr}
    
    sub lr, lr, #4            @ Corrigindo o valor de lr
    
    @ Volta para o codigo anterior a chamada 
    movs pc, lr

UNDEFINED_INSTRUCTION_HANDLER:
    b loop_nao_tratado

@       As interrupcoes por software gerada pelas chamadas do codigo arm serao
@       tratadas aqui.

@ r0-r3: parametros da syscall
@ r7: numero da syscall
@ retorno: em r0
SUPERVISOR_HANDLER:

    push {r1-r12, lr}             @ Empilha os registradores na pilha de
                                        @ supervisor

    mrs r12, spsr                       @ Salva o modo atual para evitar perde-
                                        @ lo em outra chamada de sistema

    push {r12}                          @ Empilha o modo atual tambem
    
    cmp r7, #16
    beq treat_read_sonar_svc 
    cmp r7, #17
    beq treat_register_proximity_callback_svc
    cmp r7, #18
    beq treat_set_motor_speed_svc
    cmp r7, #19
    beq treat_set_motors_speed_svc
    cmp r7, #20
    beq treat_get_time_svc
    cmp r7, #21
    beq treat_set_time_svc
    cmp r7, #22
    beq treat_set_alarm_svc
    cmp r7, #23
    beq user_back_to_alarm
    cmp r7, #24
    beq user_back_to_callback
     
@ parametros:
@ r0: identificacao do sonar a ser lido
@ retorno
@ em r0 o valor lido ou -1 se for um sonar invalido
treat_read_sonar_svc:

@ O trecho abaixo eh necessario porque podemos chamar IRQ do codigo de usuario
@ ou do proprio soul quando vamos tratar as callbacks. Por isso, sempre coloca
@ mos na pilha de usuario os parametros.
    msr CPSR_c, 0x1F           @ SYSTEM MODE com IRQ e FIQ enabled
    ldr r0, [sp]
    
    msr CPSR_c, 0x13           @ SUPERVISOR MODE com IRQ e FIQ enabled 

    ldr r1, =GPIO_BASE 
    ldr r2, [r1, #GPIO_DR]

    cmp r0, #15
    bhi end_read_invalid 
    cmp r0, #0
    blt end_read_invalid

    mov r0, r0, LSL #2          @ Coloca o multiplexador na regiao dos bits dele

    @ Mascara com 1 em tudo e 0 nas posicoes do multiplexador e do trigger
    ldr r4, =0xFFFFFFC1

    @ Mascara com 0 em tudo e 1 nas posicoes do multiplexador e do trigger
    ldr r5, =0x3E

    @ Coloca 1 em todos os bits do multiplexador e do trigger e nao altera o resto 
    orr r2, r2, r5 

    @ Coloca 1 em tudo com excecao dos bits do multiplexador e do trigger
    orr r0, r0, r4

    @ Agora passa as mudancas de r0 para r2
    and r2, r2, r0 

    @ Trigger esta sendo setado em 0 tambem
    @ SONAR_MUX <= SONAR ID e TRIGGER = 0
    str r2, [r1, #GPIO_DR]
    
    mov r3, #0
    ldr r10, =LOOP_WAIT
@ Primeiro delay de 15ms
sonar_delay_loop:
    @ Vamos esperar um pouco...
    cmp r3, r10
    add r3, r3, #1

    blo sonar_delay_loop  

@ Colocando TRIGGER = 1
SET_TRIGGER:
    orr r2, r2, #2              @ Setando o trigger
    str r2, [r1, #GPIO_DR]

    mov r3, #0

@ Segundo delay de 15ms
sonar_delay_loop_1:
    @ Vamos esperar mais um pouco...
    cmp r3, r10
    add r3, r3, #1
   
    blt sonar_delay_loop_1 

    @ Reset no trigger  
    and r2, r2, #0xFFFFFFFD
    str r2, [r1, #GPIO_DR]

flag_eq_1_loop:
    ldr r2, [r1, #GPIO_DR]
    @ Zera os bits de data e deixa o bit de flag
    and r2, r2, #1

    @ Se o bit de flag esta setado, 1 and 1 = 1;
    @ Se o bit de flag nao esta setado, 1 and 0 = 0;
    @ r5 possui 0x1 se a flag esta setada ou 0x0; se nao 
    @ Descobrir se o bit de flag esta setado 
    
    @ Verifica se o bit de flag esta setado
    cmp r2, #1 
    bne flag_eq_1_loop
    
    ldr r2, [r1, #GPIO_PSR]

    @ Seleciona os bits do sonar_data com 1 
    ldr r0, =0x3FFC0

    @ Pega os bits setados em sonar_data
    and r2, r0, r2 

    @ Pega os bits e desloca para a esquerda para pegar o valor exato
    mov r0, r2, LSR #6
  
    b end_read_normal 

end_read_invalid:
    mov r0, #-1 

end_read_normal:
    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores
    movs pc, lr                 @ E retorna

@ parametros: 
@ r0: identificao do sonar
@ r1: limitar de distancia
@ r2: ponteiro para a funcao a ser chamada caso ocorra o alarme
@ retorno:
@ r0: -1 caso o numero maximo de call_backs tenha sido excedido 
@ r0: -2 caso seja um numero invalido de sonar
@ r0: 0 em ocorrencia normal
treat_register_proximity_callback_svc:
    msr CPSR_c, 0x1F            @ System MODE com IRQ e FIQ enabled
    
    ldr r0, [sp]
    ldr r1, [sp, #4]
    ldr r2, [sp, #8]

    msr CPSR_c, 0x13            @ Supervisor MODE com IRQ e FIQ enabled 

    mov r4, r0
    mov r5, r1
    mov r6, r2 

    ldr r0, =NUM_OF_CALLBACKS
    ldr r2, [r0]

    cmp r1, #MAX_CALLBACKS
    bne check_callbacks_id 

@ Finaliza se for igual
    mov r0, #-1
    b callbacks_end 
 
check_callbacks_id: 
    cmp r4, #0 
    blt invalid_id_callback 

    cmp r4, #15
    bgt invalid_id_callback
    
    ldr r1, =CALLBACKS_ARRAY

callbacks_search_slot_loop:   
    ldr r3, [r1], #CALLBACK_STRUCT 
     
    cmp r3, #-1 
    beq callbacks_slot_found 
    b callbacks_search_slot_loop 

callbacks_slot_found:
@ Adicionando uma nova callback
    sub r1, r1, #CALLBACK_STRUCT
    str r4, [r1]
    str r5, [r1, #4]
    str r6, [r1, #8]

@ Incrementa o numero de callbacks ativas
    add r2, r2, #1
    str r2, [r0]
    mov r0, #0
    b callbacks_end

invalid_id_callback:
    mov r0, #-2

callbacks_end:
    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ parametros:
@ r0: identificador do motor
@ r1: valor de velocidade
@ retorno:
@ r0: -1 caso o identificador do motor seja invalido
@ r0: -2 caso a velocidade seja invalida
@ r0: 0 em ocorrencia normal
treat_set_motor_speed_svc:
    msr CPSR_c, 0x1F             @ System Mode com IRQ e FIQ enabled
    
    ldr r0, [sp]
    ldr r1, [sp, #4]

    msr CPSR_c, 0x13             @ Supervisor Mode com IRQ e FIQ enabled

    cmp r0, #0  
    beq set_motor_0
    cmp r0, #1
    beq set_motor_1
    b set_motor_invalid_id 

    cmp r1, #63
    bhi set_motor_invalid_v

set_motor_0:
    ldr r0, =GPIO_BASE   
    ldr r2, [r0, #GPIO_PSR]

    @ Coloca a velocidade nos bits do MOTOR0_SPEED
    mov r1, r1, LSL #19   

    @ Mascara com 0 em MOTOR0_WRITE e MOTOR0_SPEED e 1 no resto
    ldr r3, =0xFE03FFFF
    @ Mascara com 1 em MOTOR0_WRITE e MOTOR0_SPEED e 0 no resto
    ldr r4, =0x1FC0000

    @ Agora os bits de MOTOR0_WRITE e MOTOR0_SPEED estao com 1
    orr r2, r2, r4

    @ Coloca 1 em todos os bits de r1 menos em MOTOR0_WRITE e MOTOR0_SPEED
    orr r1, r1, r3

    @ Modifica apenas os bits de MOTOR0_SPEED e coloca 0 em MOTOR0_WRITE 
    and r2, r2, r1  

    str r2, [r0, #GPIO_DR] 
    
    b set_motor_normal    

set_motor_1:
    ldr r0, =GPIO_BASE   
    ldr r2, [r0, #GPIO_DR]
    
    @ Coloca a velocidade nos bits do MOTOR1_SPEED
    mov r1, r1, LSL #26   

    @ Mascara com 0 em MOTOR1_WRITE e MOTOR1_SPEED e 1 no resto
    ldr r3, =0x1FFFFFF
    @ Mascara com 1 em MOTOR1_WRITE e MOTOR1_SPEED e 0 no resto
    ldr r4, =0xFE000000

    @ Agora os bits de MOTOR1_WRITE e MOTOR1_SPEED estao com 1
    orr r2, r2, r4

    @ Coloca 1 em todos os bits de r1 menos em MOTOR1_WRITE e MOTOR1_SPEED
    orr r1, r1, r3

    @ Modifica apenas os bits de MOTOR1_SPEED e coloca 0 em MOTOR1_WRITE 
    and r2, r2, r1  

    str r2, [r0, #GPIO_DR] 
    
set_motor_normal:
    mov r0, #0
    b return_set_motor

set_motor_invalid_id:
    mov r0, #-1
    b return_set_motor 

set_motor_invalid_v:
    mov r0, #-2

return_set_motor:
    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ parametros:
@ r0: velocidade do motor 0 
@ r1: velocidade do motor 1
@ retorno:
@ r0: -1 caso a velocidade do motor 0 seja invalida 
@ r0: -2 caso a velocidade do motor 1 seja invalida 
@ r0: 0 em ocorrencia normal
treat_set_motors_speed_svc:
    msr CPSR_c, 0x1F             @ System Mode com IRQ e FIQ enabled
    
    ldr r0, [sp]
    ldr r1, [sp, #4]

    msr CPSR_c, 0x13             @ Supervisor Mode com IRQ e FIQ enabled

    cmp r0, #63
    bhi invalid_vel_0
    cmp r0, #0
    blt invalid_vel_0
    cmp r1, #63
    bhi invalid_vel_1
    cmp r1, #0
    blt invalid_vel_1

    mov r0, r0, LSL #19   
    mov r1, r1, LSL #26   
  
    orr r1, r1, r0      @ Ambas as velocidades estao no mesmo registrador agora 
    ldr r0, =GPIO_BASE  
    ldr r2, [r0, #GPIO_PSR]
   
    @ Mascara com 0 em MOTOR0_WRITE ate MOTOR1_SPEED e 1 no resto
    ldr r3, =0x3FFFF 
    
    @ Mascara com 1 em MOTOR0_WRITE ate MOTOR1_SPEED e 0 no resto
    ldr r4, =0xFFFC0000 

    @ Agora os bits de MOTOR1_WRITE e MOTOR1_SPEED estao com 1
    orr r2, r2, r4

    @ Coloca 1 em todos os bits de r1 menos em MOTOR1_WRITE e MOTOR1_SPEED
    orr r1, r1, r3

    @ Modifica apenas os bits de MOTOR1_SPEED e coloca 0 em MOTOR1_WRITE 
    and r2, r2, r1  

    str r2, [r0, #GPIO_DR] 
   
invalid_vel_0:
    mov r0, #-1
    b return_set_motors

invalid_vel_1:
    mov r0, #-2
    b return_set_motors
 
set_motors_normal:
    mov r0, #0
    b return_set_motors

return_set_motors:
    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ parametros:
@ nenhum
@ retorno:
@ r0: tempo do sistema atual
treat_get_time_svc:
    ldr r0, =INTERN_TIMER
    ldr r0, [r0] 

    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ parametros:
@ r0: tempo do sistema a ser colocado 
@ retorno:
@ nenhum
treat_set_time_svc:
    msr CPSR_c, 0x1F             @ System Mode com IRQ e FIQ enabled
    
    ldr r0, [sp]

    msr CPSR_c, 0x13             @ Supervisor Mode com IRQ e FIQ enabled

    ldr r1, =INTERN_TIMER
    str r0, [r1]

    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ parametros:
@ r0: ponteiro para a funcao a ser chamada na ocorrencia de um alarme
@ r1: tempo do sistema no qual o alarme sera tocado
@ retorno: 
@ r0: -1 caso o limite maximo de alarmes ativo seja excedido
@ r0: -2 caso o tempo do alarme seja menor que o tempo atual (alarme inutil)
@ r0: 0 em ocorrencia normal
treat_set_alarm_svc:
    msr CPSR_c, 0x1F             @ System Mode com IRQ e FIQ enabled
    

    ldr r0, [sp]
    ldr r1, [sp, #4]

    msr CPSR_c, 0x13             @ Supervisor Mode com IRQ e FIQ enabled

    mov r4, r0                   @ r4: funcao a ser chamada pelo alarme
    mov r5, r1                   @ r5: tempo do alarme ser tocado
    
    ldr r6, =NUM_OF_ALARMS 
    ldr r7, [r6]

    cmp r7, #MAX_ALARMS          @ Verifica se o numero maximo de alarmes foi
    bne test_time_alarm          @ excedido

    mov r0, #-1
    b set_alarm_end 

test_time_alarm:
    ldr r8, =INTERN_TIMER
    ldr r8, [r8]
    
    cmp r8, r5                   @ Verifica se o tempo atual eh menor ou igual 
    ble go_alarms                @ ao tempo do alarme a ser estourado

    mov r0, #-2
    b set_alarm_end

go_alarms:
    add r7, r7, #1
    str r7, [r6]

    ldr r0, =ALARM_ARRAY

search_slot_alarm_loop:
    ldr r1, [r0], #ALARM_STRUCT @ Carregando os primeiros 4 bytes de cada struct 
                                @ de alarme

    cmp r1, #-1
    bne search_slot_alarm_loop 

@ Encontrei uma posicao valida no array de alarmes 
@ Insere na posicao
    sub r0, r0, #ALARM_STRUCT   @ Corrigindo a posicao por conta do pos indexado
    str r5, [r0]
    str r4, [r0, #4]

set_alarm_end:
    pop {r12}                   @ Desempilha o modo 
    
    msr spsr, r12               @ Atualiza o modo
    
    pop {r1-r12, lr}            @ Retoma os registradores

    movs pc, lr

@ Apos realizar um alarme, voltar para o IRQ
user_back_to_alarm:
    msr CPSR_c, 0x13            @ Modo supervisor com interrupcoes
    
    pop {r12}

    ldr r0, =0xFFFFFF00
    and r12, r12, r0
    orr r12, r12, #IRQ_NO_INTERRUP          @ IRQ Mode sem interrupcoes

    msr spsr, r12
    
    pop {r1-r12, lr}              @ Registradores empilhados por 
                                  @ Supervisor Handler
    
    msr CPSR_c, #IRQ_NO_INTERRUP @ Irq Mode sem interrupcoes
    pop {r4-r12, lr} 

    b check_alarms_loop 

@ Apos tratar uma callback, voltar para o IRQ
user_back_to_callback:
    msr CPSR_c, 0x13            @ Modo supervisor com interrupcoes

    pop {r12}       

    ldr r0, =0xFFFFFF00
    and r12, r12, r0
    orr r12, r12, #IRQ_NO_INTERRUP  @ Irq mode sem interrupcoes
 
    msr spsr, r12

    pop {r1-r12, lr}

    msr CPSR_c, #IRQ_NO_INTERRUP    @ Irq mode sem interrupcoes
    b callback_return

ABORT_INSTRUCT_HANDLER:
    b loop_nao_tratado

ABORD_DATA_HANDLER:
    b loop_nao_tratado


FIQ_HANDLER:
    b loop_nao_tratado

loop_nao_tratado:
    b loop_nao_tratado

.data

CALLBACK_PROCESS: .word 0
INTERN_TIMER: .word 0
NUM_OF_CALLBACKS: .word 0
NUM_OF_ALARMS: .word 0

ALARM_ARRAY:
    .skip MAX_ALARMS*ALARM_STRUCT
@ 4 bytes para o tempo 
@ 4 bytes para o endereco 
@ /\ em cada posicao do array

CALLBACKS_ARRAY:
    .skip MAX_CALLBACKS*CALLBACK_STRUCT
@ 4 bytes para o id do sonar
@ 4 bytes para o limiar de distancia
@ 4 bytes para o endereco caso seja chamado 

@ Pilhas usadas para os modos
USER_STACK:
    .skip 512 
USER_STACK_BEGIN:

SUPERVISOR_STACK:
    .skip 512
SUPERVISOR_STACK_BEGIN:

IRQ_STACK:
    .skip 512
IRQ_STACK_BEGIN:

