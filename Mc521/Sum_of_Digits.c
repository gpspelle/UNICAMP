#include <stdio.h>
#include <math.h>

long long int ans(long long int x) {

    if(x / 10 == 0) {
        return x * (x+1) / 2;
    }

    int count = 0;
    long long int y = x;
    while(y / 10 != 0){
        count++;
        y /= 10;
    }
    int p = pow(10, count);
    return ((y * 45 * count * p / 10) + y * (y-1) * p / 2 + y * (x % p + 1) + ans(x%p));
}

int main(void) {

    long long int a, b;
    
    scanf(" %lld %lld", &a, &b);

    int sum_a = 0;
    while(a != -1) {

        printf("%lld\n", ans(b) - ans(a-1));
        scanf(" %lld %lld", &a, &b);
    }
    return 0;
}
