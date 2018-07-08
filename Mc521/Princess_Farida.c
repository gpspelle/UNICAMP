#include <stdio.h>
#define max( a, b ) ( ((a) > (b)) ? (a) : (b) )

int main(void) {

    int t;

    scanf(" %d", &t);

    long long int x = 1;
    while(t--) {

        int N, i;
        scanf(" %d", &N);
        long long int pd[10011] = {0};
        long long int pdd[10011];
        for(i = 0; i < 10011; i++) {
            pdd[i] = -1;
        }

        for(i = 0; i < N; i++) {
            scanf(" %lld", &pd[i]);
        }

        pdd[0] = pd[0];
        pdd[1] = max(pdd[0], pd[1]);
        for(i = 2; i < N; i++)  {
            pdd[i] = max(pdd[i-1], pd[i]+pdd[i-2]);
        }

        printf("Case %lld: %lld\n", x, pdd[N-1]);

        x+=1;
    }
    return 0;
}
