@ Gabriel Pellegrino da Silva

.org 0x0

.section .iv, "a"

_start:

interrupt_vector:
    b RESET_HANDLER      @ interrupt_vector[0]

.org 0x18
    b IRQ_HANDLER        @ interrupt_vector[18]

.data

CONTADOR: .word 0

IRQ_STACK:
    .skip 1024
IRQ_STACK_BEGIN:

.align 4

.text

RESET_HANDLER:

    @ Zera o contador
    ldr r2, =CONTADOR
    mov r0, #0
    str r0, [r2]
    
    ldr r0, =interrupt_vector
    mcr p15, 0, r0, c12, c0, 0
    
    msr CPSR_c,  #0x13   @ SUPERVISOR mode, IRQ/FIQ enabled 

    @ Configurando GPT
SET_GPT:
    
    @ Enderecos utilizados de GPT, nao eh o professor Guilherme
    .set GPT_BASE,              0x53FA0000
    .set GPT_CR,                0x0 
    .set GPT_PR,                0x4
    .set GPT_IR,                0xC
    .set GPT_OCR1,              0x10

    ldr r1, =GPT_BASE
    mov r0, #0x41
    str r0, [r1, #GPT_CR]
    mov r0, #0
    str r0, [r1, #GPT_PR]           

    mov r0, #1
    str r0, [r1, #GPT_IR]

    mov r0, #100
    str r0, [r1, #GPT_OCR1]
    
   @ Configurando TZIC 
SET_TZIC:
    @ Constantes para os enderecos do TZIC
    .set TZIC_BASE,             0x0FFFC000
    .set TZIC_INTCTRL,          0x0
    .set TZIC_INTSEC1,          0x84 
    .set TZIC_ENSET1,           0x104
    .set TZIC_PRIOMASK,         0xC
    .set TZIC_PRIORITY9,        0x424

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

    @ isso aqui outro codigo que teria outra permissao e o correto seria ficar tro-
@ cando o modo com o msr
wait_for_it:
    b wait_for_it

IRQ_HANDLER:

    ldr r13, =IRQ_STACK_BEGIN   @ r13 == r13_irq agora eh a pilha de IRQ

    stmfd sp!, {r0-r12}
    @ Enderecos utilizados de GPT, nao eh o professor Guilherme
    .set GPT_BASE,              0x53FA0000
    .set GPT_CR,                0x0 
    .set GPT_PR,                0x4
    .set GPT_SR,                0x8
    .set GPT_IR,                0xC
    .set GPT_OCR1,              0x10
    
    ldr r1, =GPT_BASE
    
    mov r0, #1
    str r0, [r1, #GPT_SR]
    
    ldr r1, =CONTADOR         @ Pega o endereco do contador
    ldr r2, [r1]              @ Pega o valor do contador 
    add r2, r2, #1            @ Soma um no valor
    str r2, [r1]              @ Guarda o valor atualizado

fim: 
    ldmfd sp!, {r0-r12}
    sub lr, lr, #4            @ Corrigindo o valor de lr
    
    @ Volta para o modo anterior a chamada 
    movs pc, lr


