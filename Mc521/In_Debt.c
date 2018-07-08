#include <stdio.h>

int main(void) {


    int N = 0;
    while(1) {

        scanf(" %d", &N);
        if(N == -1) {
            break;
        }
        int i;
        int p[100];
        int n_times = 0;
        int sum = 0;
        for(i = 0; i < N; i++) {
            scanf(" %d", &p[i]);
            sum += p[i];

            /* Se a soma eh multiplo de 100 */
            if(sum % 100 == 0) {
                n_times += 1;
                sum -= 100;
            }
        }
        printf("%d\n", n_times);
    }
   
     
    return 0;
}
