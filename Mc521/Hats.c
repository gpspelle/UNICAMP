#include <stdio.h>
#include <math.h>

double fat[13] = {1, 1, 2, 6, 24, 120, 720, 5040, 40320, 362880, 3628800, 39916800, 479001600};
int main(void) {

    int t, i, j;
    
    scanf("%d", &t);

    long long int der[13] = {0};
    for(i = 2; i < 13; i++) {
        double t = 0;
        for(j = 0; j <= i; j++) {
            t += pow(-1, j) / fat[j];
        }

        der[i] = fat[i] * t;
    }
    for(i = 0; i < t; i++) {
        int p;
        scanf("%d", &p);
        printf("%lld/%.0lf\n", der[p], fat[p]);
    }

    return 0;
}
