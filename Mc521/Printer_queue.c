#include <stdio.h>
#include <stdlib.h>

int absolute(int x) {
    return (x < 0) ? -x: x;
}

int main(void) {
    
    int cases, n, m, i, j;
    scanf(" %d", &cases);
    int prior[150];
    for(i = 0; i < cases; i++) {
        long long int time = 0;
        scanf(" %d %d", &n, &m);
       
        for(j = 0; j < n; j++) {
            scanf(" %d", &prior[j]);
        } 

        prior[m] = -prior[m];
        int high_prior, will_press, aux; 
        while(1) { 
            high_prior = -1;

            for(j = 0; j < n; j++) {
                if(absolute(prior[j]) > high_prior) {
                    will_press = j;
                    high_prior = absolute(prior[j]);
                }
            }
            if(will_press == 0) {
                time++;
                if(prior[0] < 0) {
                    printf("%lld\n", time);
                    break;
                } else {
                    for(j = 0; j < n-1; j++) {
                        prior[j] = prior[j+1];
                    }
                    n--;
                }
            } else {
                aux = prior[0]; 
                for(j = 0; j < n-1; j++) {
                    prior[j] = prior[j+1];
                }
                prior[n-1] = aux;
                
            }
        }
         
    }
    return 0;
}
