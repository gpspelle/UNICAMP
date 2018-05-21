#include "api_robot2.h"

/*   
 *      03  04
 *     02    05 
 *    01  FF  06
 *   00        07
 *   
 *   R1        R0
 *
 *   15        08
 *    13  RR  09
 *     14    10
 *      12  11
 *
 * */ 
motor_cfg_t r0;
motor_cfg_t r1;

void busca_parede();
void fwrd_uoli();
void stop_uoli();
void ajusta_parede();
void left_uoli();
void right_uoli();

int so0; 

int _start() {

    r0.id = 0;
    r1.id = 1;

    fwrd_uoli();
    
    /* Bora pra frente */
    while(read_sonar(3) > 1200) {

    }

    stop_uoli();

    ajusta_parede();

    while(1) {

    }
    return 0;
}

void fwrd_uoli() {
    r0.speed = 5;
    r1.speed = 5;

    set_motors_speed(&r0, &r1);
}

void stop_uoli() {
    r0.speed = 0;
    r1.speed = 0;
    
    set_motors_speed(&r0, &r1);
    
}

void left_uoli() {
    r0.speed = 4;
    r1.speed = 8;
    
    set_motors_speed(&r0, &r1);
}

void right_uoli() {
    r0.speed = 8;
    r1.speed = 4;
    
    set_motors_speed(&r0, &r1);
}

/* Coloca a parede no lado esquerdo e se aproxima dela */
void ajusta_parede() {
  
    /* Le o sonar mais a esquerda */ 
    so0 = read_sonar(0); 
    while(1) {
        r1.speed = 3;
        r0.speed = 1;
        set_motors_speed(&r0, &r1);
       
        /* Enquanto o sonar mais a esquerda nao diminuir, vire a direita */ 
        if(read_sonar(0) < so0) {
            /* Quando o sonar mais a esquerda estiver menor, esta alinhado */
            break;
        }
    }

    int d1;
    while(1) {
        /* Depois de alinhado com a parede, eh so manter o sonar 1 num limite */
        d1 = read_sonar(1);

        if(d1 > 1200) {
            right_uoli();
        }  else {
            left_uoli();
        }

    }
}
