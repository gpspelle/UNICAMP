@ Gabriel Pellegrino da Silva

.globl _start

.data

v1:             .skip 3068
v2:             .skip 3068
input_buffer:   .skip 32
output_buffer:  .skip 32
    
.text
.align 4

@ Funcao inicial
_start:
    @ Chama a funcao "read" para ler 3 caracteres da entrada padrao
    ldr r0, =input_buffer  @ endereco do que sera gravado que foi lido. 
    mov r1, #4             @ 3 caracteres + '\n'
    bl  read
    mov r4, r0

    @ Chama a funcao char_to_num para converter 3 caracteres hex em numero 
    ldr r0, =input_buffer
    mov r1, r4
    bl char_to_num 

    @ Chama a funcao pascal para comecar o tratamento das linhas 
    @ r0 possui o numero em hexadecimal de quantas linhas serao utilizadas
    bl pascal 
	
    @ Chama a funcao exit para finalizar processo.
    mov r0, #0
    bl  exit

@ Recebe 3 digitos hexadecimais e os converte para 1 numero hexadecimal 
@ parametros:
@  r0: endereco do buffer de memoria que armazena os 3 caracteres. 
@  r1: numero de bytes, sempre 3.
@ retorno:
@  r0: numero hexadecimal. 
char_to_num:    
    push {r4, r5, lr}
    mov r4, r0         @ r4 == endereco do buffer de caracteres
    mov r5, r1         @ r5 == numero de caracteres a ser considerado 
    mov r0, #0         @ number = 0
    mov r1, #0         @ loop indice
ctn_loop:
    cmp r1, r5         @ se indice == tamanho maximo
    beq ctn_end        @ finaliza conversao
    ldrb r2, [r4, r1]  @ carrega um byte da posicao r4 + r1 ( r4 + i*4 ) 
    mov r0, r0, LSL #4 @ libera 4 0's de espaco
    cmp r2, #57        @ compara ele com 57, se for maior, subtrai 55, 
                       @ se for menor, subtrai 48
    suble r2, r2, #48  
    subgt r2, r2, #55
    
    add r0, r0, r2     @ r0 += r2 
    add r1, r1, #1     @ indice++

    b ctn_loop
ctn_end:
    pop  {r4, r5, lr}
    mov  pc, lr

@ Funcao chamada para comecar a tratar as chamadas recursivas do triangulo de 
@ pascal.
@ parametros:
@  r0: numero de linhas a serem impressas.
pascal: 

    push {r4-r11, lr}
    
    mov r9, r0          @ coloca em r9 o numero de linhas 
    mov r8, #1          @ controle de indices das linhas
    ldr r10, =v1        @ r10 endereco do primeiro vetor
    ldr r11, =v2        @ r11 endereco do segundo vetor
    bl inic_vets        @ todo: isso eh necessario? inicializa os vetores com 1  
pascal_loop:

    cmp r8, r9          @ enquanto o indice de linhas nao chegar ao maximo 
    bgt pascal_end      @ se o indice for maior que o numero de linhas, termina
    cmp r8, #2          @ a partir da segunda linha, atualizo o segundo vetor 
    ble end_calc_pascal
    mov r7, #1          @ indice no segundo vetor para atualiza-lo
calc_pascal:

    cmp r7, r9          @ apenas o ultimo numero nao precisa ser atualizado
    beq end_calc_pascal @ termina a atualizacao 
    ldr r6, [r10, r7]   @ carrega em r6 o a posicao do vetor r10 acima 
    sub r7, r7, #1      @ subtrai 1 do indice para pegar o de tras
    ldr r5, [r10, r7]   @ carrega em r5 a posicao do vetor r10 de tras 
    add r5, r5, r6      @ r5 = r5 + r6
    add r7, r7, #1      @ como deu um passo pra tras, agora da 1 para a frente
    str r5, [r11, r7]   @ atualiza a posicao de r11 
    add r7, r7, #1      @ mais 1 passo para a frente
    b calc_pascal
end_calc_pascal:

    mov r7, #0          @ indice para percorrer os elementos do primeiro vetor
    mov r5, #1
print_loop:

    cmp r5, r8          @ se eu estou no ultimo elemento de uma linha, adi
                        @ ciono o '\n'
    beq add_quebra_linha
    ldr r2, [r10, r7]   @ caso contrario, adiciono um ' '
    mov r1, #8          @ r1 contem o numero de caracteres a serem convertidos
    ldr r0, =output_buffer
    bl num_to_char   
    mov r1, #' '
    ldr r0, =output_buffer
    strb r1, [r0, #8]
    b jump 
add_quebra_linha:

    ldr r2, [r10, r7]
    mov r1, #8          @ r1 contem o numero de caracteres a serem convertidos
    ldr r0, =output_buffer
    bl num_to_char   
    mov r1, #'\n'
    ldr r0, =output_buffer
    strb r1, [r0, #8]
jump:

    mov r1, #9          @ numero de caracteres a serem impressos
    bl write
    cmp r5, r8
    beq transfer        @ se ja acabou, pula para a troca dos vetores
    add r7, r7, #1      @ se nao acabou, adiciona mais um e executa de novo
    add r5, r5, #1 

    b print_loop 
transfer:

    mov r6, r11
    mov r11, r10
    mov r10, r6

    add r8, r8, #1
    b pascal_loop 
pascal_end:

    pop  {r4-r11, lr}
    mov  pc, lr

@ Converte um numero binario em uma sequencia de caracteres
@ parametros:
@  r0: endereco do buffer de memoria que recebera a sequencia de caracteres.
@  r1: numero de caracteres a ser considerado na conversao
@  r2: numero a ser convertido
num_to_char:

    push {r4-r7, lr}
    mov r5, r1          @ r5 == numero de caracteres a serem convertidos
    mov r4, #0          @ loop indice
    ldr r6, =0xF0000000 @ pegar apenas o primeiro byte
    mov r3, #28         @ quantidade de casas a serem shiftadas
ntc_loop:

    cmp r4, r5          @ se indice == tamanho maximo
    beq ntc_end         @ finaliza a conversao
    
    and r7, r6, r2      @ seleciona de r2 o byte mais a esquerda 
    mov r7, r7, LSR r3  @ deslocando esse byte para a extremidade direita 
    
    cmp r7, #10

    addlt r7, r7, #48   @ converte para caracter
    addge r7, r7, #55 

    strb r7, [r0], #1

    sub r3, r3, #4      @ deslocar 4 a menos 
    add r4, r4, #1      @ avanca o contador 
    mov r6, r6, LSR #4  @ agora vamos selecionar outro byte 
    b ntc_loop
ntc_end:

    pop {r4-r7, lr}
    mov pc, lr    

@ Inicializa os vetores v1 e v2 com 1 em todas as posicoes  
inic_vets:

    push {r4-r11, lr}
    ldr r5, =v1         @ carrega o endereco de v1
    ldr r6, =v2         @ carrega o endereco de v2
    mov r4, #0          @ inicializa um indice com 0
    mov r11, #1         @ valor a ser inicializado
    ldr r9, =0x2ff
inic_loop:

    cmp r4, r9          @ verifica se ja acabou
    bgt inic_end        @ termina o laco 
    str r11, [r5]       @ coloca 1 no endereco de r5
    str r11, [r6]       @ coloca 1 no endereco de r6
    add r5, r5, #4      @ avanca os enderecos dos vetores
    add r6, r6, #4
    add r4, r4, #1      @ e do contador
    b inic_loop
inic_end: 

    pop {r4-r11, lr}
    mov pc, lr
    
@ Le uma sequencia de bytes da entrada padrao.
@ parametros:
@  r0: endereco do buffer de memoria que recebera a sequencia de bytes.
@  r1: numero maximo de bytes que pode ser lido (tamanho do buffer).
@ retorno:
@  r0: numero de bytes lidos.
read:

    push {r4, r5, r7, lr}
    mov r4, r0
    mov r5, r1
    mov r0, #0         @ stdin file descriptor = 0
    mov r1, r4
    mov r2, r5
    mov r7, #3         @ r7 == 3 -> read
    svc 0x0
    pop {r4, r5, r7, lr}
    mov pc, lr

@ Escreve uma sequencia de bytes na saida padrao.
@ parametros:
@  r0: endereco do buffer de memoria que contem a sequencia de bytes.
@  r1: numero de bytes a serem escritos
write:
    push {r4,r5, r7, lr}
    mov r4, r0
    mov r5, r1
    mov r0, #1         @ stdout file descriptor = 1
    mov r1, r4         @ endereco do buffer
    mov r2, r5         @ tamanho do buffer.
    mov r7, #4         @ write
    svc 0x0
    pop {r4, r5, r7, lr}
    mov pc, lr

@ Finaliza a execucao de um processo.
@  r0: codigo de finalizacao (Zero para finalizacao correta)
exit:    
    mov r7, #1         @ syscall number for exit
    svc 0x0
