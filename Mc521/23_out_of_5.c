#include <stdio.h>

int dfs(long long int vetor[5], int profundidade, int sum, long long int utilizados[5]) {

    long long int i;
    if(sum == 23 && profundidade == 5) {
        return 1; 
    } else {
        int resultado = 0;
        for(i = 0; i < 5; i++) {
            if(utilizados[i] == 1) {
                continue;
            } else {

                /* vou utilizar o i */
                utilizados[i] = 1;
                resultado += dfs(vetor, profundidade + 1, sum + vetor[i], utilizados); 
                resultado += dfs(vetor, profundidade + 1, sum - vetor[i], utilizados); 
                resultado += dfs(vetor, profundidade + 1, sum * vetor[i], utilizados); 
                /* libera o i pra outro nivel */
                utilizados[i] = 0;
            }
        }
        return resultado;
    }
}

int main(void) {

    while(1) {
        long long int x1, x2, x3, x4, x5;
        long long int i;
        scanf(" %lld %lld %lld %lld %lld", &x1, &x2, &x3, &x4, &x5);

        long long int vetor[5];
        long long int utilizados[5] = {0};
        vetor[0] = x1;
        vetor[1] = x2;
        vetor[2] = x3;
        vetor[3] = x4;
        vetor[4] = x5;

        if(x1 == x2 && x1 == x3 && x1 == x4 && x1 == x5 && x1 == 0)
            break;
   
        long long int resultado = 0;
        for(i = 0; i < 5; i++) {
            /* o i-esimo ja foi uzado */
            utilizados[i] = 1;
            resultado += dfs(vetor, 1, vetor[i], utilizados); 
            /* agora posso usar o i denovo na proxima chamada em outra profundidade */
            utilizados[i] = 0;
        } 

        if(resultado) {
            printf("Possible\n");
        }
        else {
            printf("Impossible\n");
        }
        
    }

    return 0;
}
