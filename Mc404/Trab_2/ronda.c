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


void ronda();
void turn_right();
void stop_uoli();
void fwrd_uoli();
void curva();
void desvia();

motor_cfg_t m0;
motor_cfg_t m1;

int relogio;
int tempo;
int old_relogio;
int aux = 1;
int so3 = 0;
int flag = 0;
int _start() {
    tempo = 0;
    set_time(0);

    m0.id = 0;
    m1.id = 1;
 
    ronda();
     
    while(1) {
        if(tempo == 50) {
            break;
        }
    }
    
    _start(); 

    return 0;
}

void ronda() {
    
    /* Anda para a frente*/
    fwrd_uoli();
    get_time(&old_relogio);

    while(1) {
        if(read_sonar(3) < 1200) {
            flag = 1;
            break;
        }

        get_time(&relogio);
        /* Verifica se ele ja andou para frente o que deveria andar */
        if((old_relogio + 100*tempo) <= relogio) {
            break;
        }
    }

    /* Depois de desviar a parede para o robo e aguarda o alarme acontecer */
    if(flag) {
        stop_uoli();
        flag = 0;
    }
    /* Coloca um alarme no fim da caminhada para a frente */
    get_time(&relogio);
    aux = tempo*100;
    add_alarm(curva, aux + relogio);
    tempo += 1;

}

void curva() {
    turn_right();

    aux = 7500;
    get_time(&relogio);
    add_alarm(ronda, relogio + aux);
}

void turn_right() {
    m0.speed = 0;
    m1.speed = 3;    
    
    set_motors_speed(&m0, &m1);

}

void fwrd_uoli() {
    m0.speed = 3;
    m1.speed = 3;

    set_motors_speed(&m0, &m1);
}

void stop_uoli() {
    m0.speed = 0;
    m1.speed = 0;

    set_motors_speed(&m0, &m1);
}
