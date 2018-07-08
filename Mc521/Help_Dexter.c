#include <stdio.h>
#include <math.h>

long long int num1[131072][18] = {0};  

int main(void) {
   
    int T, i;
    scanf(" %d", &T); 

    for(i = 0; i < T; i++) {
        int p, q, j, k, l;
        long long int numero, index = 0;
        scanf(" %d %d", &p, &q);

        numero = pow(2, q);
         

        for(j = 0; j < p; j++) {
            num1[index][j] = 1;
        }

        for(j = 0; j < p; j++) {
            long long int aux = index;
            for(k = 0; k < aux+1; k++) {
                long long int num2[18];
                for(l = 0; l < p; l++) {
                    num2[l] = num1[k][l];
                }
                if(num1[k][j] == 1) {
                    num2[j] = 2;
                } else {
                    num2[j] = 1;
                }
                
                index++;
                for(l = 0; l < p; l++) {
                    num1[index][l] = num2[l];
                }
            }
        }

        long double smaller = 1500000000000000000;
        long double bigger = -1;

        for(j = 0; j < index+1; j++) {
            long long int k = 1;
            num1[j][17] = 0;
            for(l = p-1; l >= 0; l--) {
                num1[j][17] += num1[j][l] * k; 
                k*=10;
            }
        }


        for(j = 0; j < index+1; j++) {
            if(num1[j][17] % numero == 0) {
                if(num1[j][17] < smaller) {
                    smaller = num1[j][17];
                }
                if(num1[j][17] > bigger) {
                    bigger = num1[j][17];
                }
            } 
        } 

        if(smaller == bigger) {
            printf("Case %d: %.0LF\n", i+1, smaller);
        } else if(smaller < bigger) {
            printf("Case %d: %.0LF %.0LF\n", i+1, smaller, bigger);

        } else {
            printf("Case %d: impossible\n", i+1);

        }
    }
    return 0;
}
