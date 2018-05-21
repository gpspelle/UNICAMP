@ Gabriel Pellegrino da Silva
.global ajudaORobinson

.align 4

.data

output_buffer:  .skip 32

.text

.align 4

@ funcao chamada pela main    
ajudaORobinson:
  
    bl posicaoXRobinson
    mov r4, r0          @ guarda em r1 a posicao x do Robinson

    bl posicaoYRobinson
    mov r5, r0          @ guarda em r2 a posicao y do Robinson

    bl posicaoXLocal
    mov r10, r0          @ guarda em r3 a posicao x do local

    bl posicaoYLocal
    mov r11, r0          @ guarda em r4 a posicao y do local

    mov r1, r4
    mov r2, r5
    mov r3, r6
    mov r4, r7
    mov r0, #0          @ r0 valendo 0 significa que ainda nao acabamos
    bl dfs
    
    mov pc, lr
         
@ depth-first-search
@ todo: arrumar uma variavel com retorno 1 ou 0 para verificar se achei ou nao
@ r1: posicao x do Robinson
@ r2: posicao y do Robinson
@ r10: posicao x do Local
@ r11: posicao y do Local
@ retorno:
@ r0: 1 se foi encontrado caminho, 0 se nao foi.
@ todo: testar no comeco se foi encontrado um caminho
dfs:

    push {r5-r8, lr} 

    mov r5, r1          @ salva o x da celula atual
    mov r6, r2          @ salva o y da celula atual
       
    @ Verifica se c eh o local desejado
    cmp r5, r10
    beq x_igual 
    bne nao_igual
x_igual:

    cmp r6, r11
    beq y_igual
    bne nao_igual
y_igual:
    
    @ chegar aqui significa que acabou; ou seja, encontramos um caminho
    mov r0, #1 
    mov r7, r5
    mov r8, r6
    b   encontramos

nao_igual:

    push {r0-r3, lr}
    mov r0, r5          @ retorna para r0 x da celula atual
    mov r1, r6          @ retorna para r1 y da celula atual

    @ Atualiza a posicao r0 e r1 como visitada
    bl visitaCelula
    pop {r0-r3, lr} 

    @ Trecho de codigo verificar se da para passar por algum dos vizinho
    mov r7, r5          @ cria uma copia do x atual
    mov r8, r6          @ cria uma copia do y atual
vizinho_1:

    sub r7, r7, #1      @ /\ x 

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ todo: conferir de daParaPassar poe 0 ou 1 em r0
                        @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_2

    bl testa_visitado
    cmp r0, #1
    bne vizinho_2
    mov r7, r1
    mov r8, r2
    beq encontramos
    @ Caso o vizinho possa ser visitado, ele sera
    @ Caso contrario, ira continuar esse codigo daqui 
vizinho_2:
    
    add r8, r8, #1      @ /\ ->

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_3
    bl testa_visitado
    cmp r0, #1
    bne vizinho_3
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_3:

    add r7, r7, #1      @ /\ -> \/

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_4
    bl testa_visitado
    cmp r0, #1
    bne vizinho_4
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_4:

    add r7, r7, #1      @ /\ -> \/ \/

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_5
    bl testa_visitado
    cmp r0, #1
    bne vizinho_5
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_5:

    sub r8, r8, #1      @ /\ -> \/ \/ <-

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_6
    bl testa_visitado
    cmp r0, #1
    bne vizinho_6
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_6:

    sub r8, r8, #1      @ /\ -> \/ \/ <- <-

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_7
    bl testa_visitado
    cmp r0, #1
    bne vizinho_7
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_7:

    sub r7, r7, #1      @ /\ -> \/ \/ <- <- /\

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_8
    bl testa_visitado
    cmp r0, #1
    bne vizinho_8
    mov r7, r1
    mov r8, r2
    beq encontramos
vizinho_8:

    sub r7, r7, #1      @ /\ -> \/ \/ <- <- /\ /\

    push {r1-r3, lr}

    mov r0, r7          @ transfere r7 para r0 ( X )
    mov r1, r8          @ transfere r8 para r1 ( Y ) 

    @ Verifica se a nova posicao eh valida
    bl daParaPassar 
    cmp r0, #0          @ Se o valor de r0 eh 1, eh porque da pra passar
                        @ Caso contrario, temos um 'X' ou acabou o mapa
    pop {r1-r3, lr}
    beq vizinho_end_0
    bl testa_visitado
    cmp r0, #1
    bne vizinho_end_0
    mov r7, r1
    mov r8, r2
    beq encontramos
testa_visitado:

    push {r0-r8, lr} 
    
    mov r0, r7
    mov r1, r8

    bl foiVisitado
    cmp r0, #0 

    bleq visite_vizinho

    pop {r0-r8, lr}
    mov pc, lr
visite_vizinho:

    push {r1, r2, r7, r8, r10, r11, lr}

    mov r1, r7
    mov r2, r8
    bl dfs
    
    pop {r1, r2, r7, r8, r10, r11, lr}
    
    mov pc, lr
encontramos:
  
    push {r0-r2} 
    mov r2, r7
    ldr r0, =output_buffer 
    mov r1, #8
    
    cmp r2, #10

    addlt r2, r2, #48   @ converte para caracter
    addge r2, r2, #55 

    str r2, [r0]

    mov r1, #1
    
    bl write

    mov r1, #' '
    ldr r0, =output_buffer      @ isso eh necessario?
    
    str r1, [r0] 
   
    mov r1, #1

    bl write 

    mov r2, r8
    ldr r0, =output_buffer 
    mov r1, #8
    
    cmp r2, #10

    addlt r2, r2, #48   @ converte para caracter
    addge r2, r2, #55 

    str r2, [r0]

    mov r1, #1
    
    bl write

    mov r1, #'\n'
    ldr r0, =output_buffer      @ isso eh necessario?
    
    str r1, [r0] 
   
    mov r1, #1

    bl write 
    
    pop {r0-r2}

    mov r0, #1 
    pop {r5-r8, lr}
    mov pc, lr      
vizinho_end_0:
    @ Chegar aqui significa que nenhum vizinho eh possivel de ser visitado
    mov r0, #0 

    pop {r5-r8, lr} 
    mov pc, lr

vizinho_end_1:
    @ Chegar aqui significa que nenhum vizinho eh possivel de ser visitado
    mov r0, #1 

    pop {r5-r8, lr} 
    mov pc, lr

@ Converte um numero binario em uma sequencia de caracteres
@ parametros:
@  r0: endereco do buffer de memoria que recebera a sequencia de caracteres.
@  r1: numero de caracteres a ser considerado na conversao
@  r2: numero a ser convertido
num_to_char:

    push {r3-r11, lr}
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

    pop {r3-r11, lr}
    mov pc, lr    

@ Escreve uma sequencia de bytes na saida padrao.
@ parametros:
@  r0: endereco do buffer de memoria que contem a sequencia de bytes.
@  r1: numero de bytes a serem escritos
write:
    push {r0, r1, r2, r4 ,r5, r7, r10, r11, lr}
    mov r4, r0
    mov r5, r1
    mov r0, #1         @ stdout file descriptor = 1
    mov r1, r4         @ endereco do buffer
    mov r2, r5         @ tamanho do buffer.
    mov r7, #4         @ write
    svc 0x0
    pop {r0, r1, r2, r4, r5, r7, r10, r11, lr}
    mov pc, lr

@ Finaliza a execucao de um processo.
@  r0: codigo de finalizacao (Zero para finalizacao correta)
exit:    
    mov r7, #1         @ syscall number for exit
    svc 0x0


@ vim:ft=armv5
