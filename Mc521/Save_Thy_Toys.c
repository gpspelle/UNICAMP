#include <stdio.h>

#define max(x, y) (((x) > (y)) ? (x) : (y))

int main(void) {

    int T;

    scanf(" %d", &T);

    while(T--) {
        long int  N, i;
        long int toys[100010] = {0};
        long int prog_dinamica[100010] = {0};
        scanf(" %ld", &N);
        for(i = 0; i < N; i++) {
            scanf(" %ld", &toys[i]);
        }

        for(i = N-1; i >= 0; i--) {

            /* o valor atual eh pegar um brinquedo e o acumulado dos proximos */
            prog_dinamica[i] = toys[i] + prog_dinamica[i+2];
            
            /* nao deixe o sheldon pegar tudo */
            if(i+1 < N) {
                /* o valor atual eh pegar dois brinquedos e o acumulado dos proximos */
                prog_dinamica[i] = max(prog_dinamica[i], toys[i] + toys[i+1] + prog_dinamica[i+4]);   
            }
            /* nao deixe o sheldon pegar tudo */
            if(i+2 < N) {
                
                /* o valor atual eh pegar tres brinquedos e o acumulado dos proximos */
                prog_dinamica[i] = max(prog_dinamica[i], toys[i] + toys[i+1] + toys[i+2] + prog_dinamica[i+6]);   
            }
        }

        printf(" %ld\n", prog_dinamica[0]); 
    }
    return 0;
}
