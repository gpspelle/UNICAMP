@ Gabriel Pellegrino da Silva

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@
@ Codigo de exemplo para controle basico de um robo.
@ Este codigo le os valores de 2 sonares frontais para decidir se o
@ robo deve parar ou seguir em frente.
@ 2 syscalls serao utilizadas para controlar o robo:
@   write_motors  (syscall de numero 124)
@             Parametros:
@             r0 : velocidade para o motor 0  (valor de 6 bits)
@             r1 : velocidade para o motor 1  (valor de 6 bits)
@
@  read_sonar (syscall de numero 125)
@             Parametros:
@             r0 : identificador do sonar   (valor de 4 bits)
@             Retorno:
@             r0 : distancia capturada pelo sonar consultado (valor de 12 bits)
@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

        
.text
.align 4
.globl _start

_start:                         @ main
        
        mov r0, #0              @ Carrega em r0 a velocidade do motor 0.
                                @ Lembre-se: apenas os 6 bits menos significati
                                @ vos serao utilizados.
        mov r1, #0              @ Carrega em r1 a velocidade do motor 1.
        mov r7, #124            @ Identifica a syscall 124 (write_motors).
        svc 0x0                 @ Faz a chamada da syscall.

        ldr r6, =1200           @ r6 <- 1200 (Limiar para parar o robo)

loop:        
        mov r0, #3              @ Define em r0 o identificador do sonar a ser 
                                @ consultado
        mov r7, #125            @ Identifica a syscall 125 (read_sonar).
        svc 0x0                 
        mov r5, r0              @ Armazena o retorno da syscall.

        mov r0, #4              @ Define em r0 o sonar.
        mov r7, #125
        svc 0x0
        mov r8, r0              @ Armazena o retorno da outra syscall.
analyse:
        cmp r5, r6
        blt left_or_right
        cmp r8, r6
        blt left_or_right
forward:
        mov r0, #16
        mov r1, #16
        mov r7, #124
        svc 0x0
        
        b loop
left_or_right:
        cmp r5, r8
        bge turn_right
        blt turn_left
turn_right:
        mov r0, #0              
        mov r1, #16
        mov r7, #124
        svc 0x0

        mov r0, #4              @ Define em r0 o identificador do sonar a ser 
                                @ consultado
        mov r7, #125            @ Identifica a syscall 125 (read_sonar).
        svc 0x0 
        cmp r0, r6 
        bge loop
        blt turn_right
turn_left:
        mov r0, #16         
        mov r1, #0
        mov r7, #124
        svc 0x0

        mov r0, #3              @ Define em r0 o identificador do sonar a ser 
                                @ consultado
        mov r7, #125            @ Identifica a syscall 125 (read_sonar).
        svc 0x0 
        cmp r0, r6 
        bge loop
        blt turn_left
end:                            @ nunca vai parar essa caceta
        mov r7, #1              @ syscall exit
        svc 0x0

@ vim:ft=armv5
