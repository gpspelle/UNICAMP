#include <stdio.h>

long long int v[1000010] = {0};

int main(void) {
    long long int i = 0;
    v[4] = 1;
    for(i = 5; i < 1000001; i++) {
        long long int t = i / 2 - 1;
        long long int x = i % 2;
        if(x == 1)
            x=0;
        else
            x=1;
        v[i] = v[i-1] + t * ( t+1 ) - x * t;
    }
    int f;
    scanf("%d", &f);
    int k;
    for(i = 0; i < f; i++) {
        scanf(" %d", &k); 
        printf("%lld\n", v[k]);
    }
    return 0;
}
