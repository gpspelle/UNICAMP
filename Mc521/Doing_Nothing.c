#include <stdio.h>

int main(void) {

    int N, M, i;

    scanf(" %d %d", &N, &M);

    while(N != -1) {

        long long int pd[1000000] = {0};
        
        long long int k, sum = 0;     
        for(i = 0; i < N; i++) {
            scanf(" %lld", &k);
            pd[i] = k;
            if(i > 0)
                pd[i] += pd[i-1];
            sum += pd[i];
        }
        printf("%lld\n", sum*M);

        scanf(" %d %d", &N, &M);
    }
    return 0;
}
