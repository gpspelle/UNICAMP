@       Descricao: codigo em arm para realizar as chamadas de funcoes prescri-
@       tas na api_robot2.h.
@       
@       Autor: Gabriel Pellegrino da Silva (gpsunicamp016@gmail.com)
@
@       Data: 2017/2S

.text 

@@@@@ MOTORES @@@@@@

.global set_motor_speed
@  r0: ponteiro para o motor contendo o id e a sua velocidade
@  retorno: void
set_motor_speed:
    push {r6, r7, lr}
    ldrb r1, [r0, #1]  
    ldrb r0, [r0]
    mov r7, #18

@ Esse push e pop em r0 ocorre porque nao necessariamente vou chamar um
@ read_sonar a partir daqui. E como a pilha de usuario foi decidido como
@ sendo o meio de passagem dos parametros, ele tem de ser empilhado, mesmo que
@ diretamente visivel.
    push {r0, r1}
    svc 0x0

    mov r6, r0
    pop {r0, r1}
    mov r0, r6

    pop {r6, r7, lr}
    mov pc, lr

.global set_motors_speed
@  r0: ponteiro para o motor contendo o id e a sua velocidade
@  r1: ponteiro para o motor contendo o id e a sua velocidade
@  retorno: void
set_motors_speed:
    push {r7, lr}

    mov r2, r0
    mov r3, r1
    ldrb r0, [r2]       @ r2 tem 0 ou 1 
    ldrb r1, [r3]       @ r3 tem 0 ou 1

    cmp r0, #0
    beq normal_motors_speed 
    bne inverted_motors_speed 

@ Comentario do caus, talvez nao precise tratar a inversao, mas se 
@ precisar, ta ai
normal_motors_speed: 
    ldrb r0, [r2, #1]    
    ldrb r1, [r3, #1]
    
    b go_set_motors_speed
inverted_motors_speed:
    ldrb r0, [r3, #1]
    ldrb r1, [r2, #1]
    
go_set_motors_speed:
    mov r7, #19

    push {r0, r1}
    svc 0x0
    pop {r0, r1}

    pop {r7, lr}
    mov pc, lr

@@@@@ SONARES @@@@@@

.global read_sonar
@ r0: id do sonar a ser lido
@ retorno: unsigned short com o valor da distancia lido pelo sonar
read_sonar:
    push {r7, r8, lr}
    mov r7, #16 

@ Nesse caso, empilho o parametro, mas nao recupero, porque se nao eu perco
@ o resultado da leitura
    push {r0}
    svc 0x0
    pop {r8}

    pop {r7, r8, lr}
    mov pc, lr

.global read_sonars
@ r0: id inicial de leitura
@ r1: id final de leitura (inclusive)
@ r2: ponteiro para o vetor de unsigned ints contendo os valores lidos
@ retorno: void
read_sonars:
    push {r4-r12, lr}
    mov r4, r0          @ r4 contem o inicio
    mov r5, r1          @ r5 contem o fim
    mov r6, #0          @ Contador do vetor de posicoes 
    mov r7, #16         @ id da syscall
    mov r9, r2
    
read_sonars_loop:
    mov r0, r4

@ Nesse caso, empilho o parametro, mas nao recupero, porque se nao eu perco
@ o resultado da leitura
    push {r0}
    svc 0x0
    pop {r8} 
    mov r8, r6, LSL #2
    str r0, [r9, r8]

    add r6, r6, #1
    add r4, r4, #1

    cmp r4, r5
    bgt read_sonars_end
    b read_sonars_loop

read_sonars_end:
    @ O tamanho desse vetor eh id_final - id_inicial + 1
    pop {r4-r12, lr}
    mov pc, lr

.global register_proximity_callback
@ r0: id do sensor que deve ser monitorado
@ r1: valor limite de distancia
@ r2: ponteiro para a funcao que deve ser chamada quando o limite de distancia
@ for atingido
@ retorno: void
register_proximity_callback:
    push {r7, lr}

    mov r7, #17   
    
    push {r0-r2}
    svc 0x0 
    pop {r0-r2}

    pop {r7, lr}
    mov pc, lr

@@@@@ CONTROLE DE TEMPO @@@@@


.global add_alarm
@ r0: ponteiro para uma funcao a ser chamada quando o alarme for acionado
@ r1: momento de acionar o alarme
@ retorno: void
add_alarm: 
    push {r7, lr}
    
    mov r7, #22  
    
    push {r0, r1}
    svc 0x0
    pop {r0, r1} 

    pop {r7, lr}
    mov pc, lr

.global get_time
@ r0: ponteiro para onde sera colocado o tempo atual lido
@ retorno: void
get_time:
    push {r4, r7, lr}
  
    mov r4, r0 
    mov r7, #20 

    svc 0x0

    str r0, [r4]

    pop {r4, r7, lr}
    mov pc, lr

.global set_time
@ r0: valor do tempo a ser setado
@ retorno: void
set_time:
    push {r7, lr}

    mov r7, #21

    push {r0}
    svc 0x0
    pop {r0}

    pop {r7, lr}
    mov pc, lr

