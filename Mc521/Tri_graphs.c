#include <stdio.h>

long long int get_min_2(long long int a, long long int b) {
    long long int min = 1000005;
    if (a < min)
        min = a;
    if (b < min) 
        min = b;

    return min;
}

int main(void ) {
    
    long long int N, i;

    scanf(" %lld", &N);
    
    long long int tc = 1;
    long long int p[2][3] = {0};
    while (N != 0) {

        long long int x1, x2, x3;
        scanf(" %lld %lld %lld", &x1, &x2, &x3);  
        
        p[0][0] = 1000000000; 
        p[0][1] = x2;
        p[0][2] = x2 + x3;

        int r = 1;
        int r_ = 0;
        for(i = 1; i < N; i++) {
            scanf(" %lld %lld %lld", &x1, &x2, &x3);  
            printf("%lld %lld %lld\n", p[r_][0], p[r_][1], p[r_][2]);
            p[r][0] = x1 + get_min_2(p[r_][0], p[r_][1]); 
            p[r][1] = x2 + get_min_2(get_min_2(p[r][0], p[r_][0]), get_min_2(p[r_][1], p[r_][2]));
            p[r][2] = x3 + get_min_2(p[r][1], get_min_2(p[r_][1], p[r_][2]));

            r = r^1;
            r_ = r^1;
        }

        printf("%lld. %lld\n", tc++, p[r_][1]);
        scanf(" %lld", &N);
    }


    return 0;
}
