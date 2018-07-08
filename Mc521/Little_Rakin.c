#include <stdio.h>
#include <math.h>

int get_primos(int a, int b, int matrix[41][340][3]) {
  
    int pos = 0;
    int i;
    int aux1 = a, aux2 = b;

    while(a % 2 == 0) {
        a = a / 2;
        matrix[0][pos][0] += 1; 
    } 
   
    if(aux1 % 2 == 0) {
        matrix[0][pos][2] = 0;
        matrix[0][pos++][1] = 2; 
    }
    while(b % 2 == 0) {
        b = b / 2;
        matrix[1][pos][0] += 1; 
    } 
    
    if(aux2 % 2 == 0) {
        matrix[0][pos][2] = 1;
        matrix[1][pos++][1] = 2; 
    }
    for(i = 3; i <= sqrt(a); i += 2) {
        aux1 = a;
        while(a % i == 0) {
            matrix[0][pos][0] += 1;
            a = a / i;
        }
        if(aux1 % i == 0) {
            matrix[0][pos][2] = 0;
            matrix[0][pos++][1] = i;
        } 
    }
  
    if(a > 2) {

        matrix[0][pos][2] = 0;
        matrix[0][pos][0] = 1;
        matrix[0][pos++][1] = a;
    } 

    for(i = 3; i <= sqrt(b); i += 2) {
        aux1 = b;
        while(b % i == 0) {
            matrix[1][pos][0] += 1;
            b = b / i;
        }
        if(aux1 % i == 0) {
            matrix[0][pos][2] = 1;
            matrix[1][pos++][1] = i;
        } 
    }

    if(b > 2) {
        matrix[0][pos][2] = 1;
        matrix[1][pos][0] = 1;
        matrix[1][pos++][1] = b;
    }

    return pos; 
}

void insertion(int vetor[41][340][3], int tamanho, int n) {

        int i, j, auxiliar;
        for(i=1; i<tamanho; i++) {
            j=i;
            while(j>0) {
                if(vetor[0][j][1] < vetor[0][j-1][1]) {
                    auxiliar = vetor[0][j][1];
                    vetor[0][j][1] = vetor[0][j-1][1];
                    vetor[0][j-1][1] = auxiliar;
                    auxiliar = vetor[n][j][0];
                    vetor[n][j][0] = vetor[n][j-1][0];
                    vetor[n][j-1][0] = auxiliar;
                }
                j--;
            }
        }
}
int main(void) {

    int T, i, j, k, l;
    scanf("%d", &T);

    for(i = 0; i < T; i++) {
        int n, a, b, m;
        scanf(" %d %d %d", &n, &a, &b);
       
        int matrix_primos[41][340][3] = {0};  
      
        m = get_primos(a, b, matrix_primos);

        for(j = 2; j <= n; j++) {
            for(l = 0; l < m; l++) {
                matrix_primos[j][l][0] = matrix_primos[j-1][l][0] + matrix_primos[j-2][l][0];
            }
        }
       
        for(j = 0; j < m; j++) {
            for(l = 0; l < m; l++) {
                if((matrix_primos[0][j][1] == matrix_primos[1][l][1]) && matrix_primos[0][j][1] != 0) { 
                    matrix_primos[n][j][0] += matrix_primos[n][l][0];
                    matrix_primos[n][l][0] = -1;
                }
            }
        } 

        for(j = 0; j < m; j++) {
            matrix_primos[0][j][1] += matrix_primos[1][j][1];
        }
        
        insertion(matrix_primos, m, n); 

        for(j = 0; j < m; j++) {
            if(matrix_primos[n][j][0] != -1) {
                printf("%d %d\n", matrix_primos[0][j][1], matrix_primos[n][j][0]);
            }
        }

        printf("\n");
    }
    return 0; 
}
