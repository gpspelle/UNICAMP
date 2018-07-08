#include <stdio.h>
#include <math.h>

long long int dp[2000002] = {0};
int main(void) {

    long int t, i;

    dp[0] = 0;
    dp[1] = 0;


    for(long int i = 2; i <= 1000000; i++) {
        long int j = 2*i;

        while(j <= 1000000) {
            dp[j] = dp[j] + i;
            j += i;
        }

        dp[i] += 1;
    }

    for(i = 2; i <= 1000000; i++) {
        dp[i] += dp[i-1];
    }

    scanf("%ld", &t);

    /*printf("Passei\n");*/
    for(i = 0; i < t; i++) {
        long int val;
        scanf(" %ld", &val);
        printf("%lld\n", dp[val]);
    }
    
    return 0;
}
