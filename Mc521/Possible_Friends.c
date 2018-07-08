#include <stdio.h>
#include <string.h>
#define min(a,b)            (((a) < (b)) ? (a) : (b))
int main(void) {

    int t, i, j, k;   
    scanf(" %d", &t);

    while(t --) {
        
        char m[60][60];
        scanf(" %s", m[0]);
        int tam = strlen(m[0]);
        int p[60]; 
        p[0] = 0;
        for(i = 1; i < tam; i++) {
            p[i] = 0;
            scanf(" %s", m[i]);
        }

        for(i = 0; i < tam; i++) {
            int amigos[60] = {0};
            for(j = 0; j < tam; j++) {

                if (i == j)
                    continue;
                
                if(m[i][j] == 'Y') {
                    for(k = 0; k < tam; k++) {
                        if (k == i)
                            continue;
                        
                 /*       printf("I: %d - J: %d - K: %d - m[j][k] = %c - m[i][k] = %c - amigos[k] = %d\n", i, j, k, m[j][k], m[i][k], amigos[k]); */

                        if(m[j][k] == 'Y' && m[i][k] == 'N' && amigos[k] == 0) {
                            p[i] += 1;
                            amigos[k] = 1;
                        }
                    }
                }
            }
        }
        
        /*for(i = 0; i < tam; i++) {
            printf(" %d ", p[i]);
        }
        printf("\n");*/
        int max = -1;
        int index = -1;
        
        for(i = 0; i < tam; i++) {
            if (p[i] > max) {
                max = p[i];
                index = i;
            }
        }

        /*for(i = 0; i < tam; i++) {
            for(j = 0; j < tam; j++) {
                printf("%c", m[i][j]);
            }
            printf("\n");
        }*/
        printf("%d %d\n", index, max); 
    }
    return 0;
}
