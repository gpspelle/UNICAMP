#include <iostream>
using namespace std;

long long int maior_divisor_comum(long long int x, long long int y) {
    if(x == y)
        return x;

    if(x > y) {
        return maior_divisor_comum(x-y, y);
    } else {
        return maior_divisor_comum(x, y-x);
    }

}

int main(void) {

    long long int x, y;
    long long int a, b;

    long long int resp = 0;

    cin >> x >> y >> a >> b;
    long long int max_xy, min_xy, aux_min;
    if( x > y ) {
        max_xy = x;
        min_xy = y;
    }
    else {
        max_xy = y;
        min_xy = x;
    }

    long long int  min_multiplo_comum = ( x * y )  / maior_divisor_comum(x, y);
    
    aux_min = min_multiplo_comum;

    if(aux_min < a) {
        if (a % min_multiplo_comum == 0) {
            min_multiplo_comum = a;
        }
        else {
            while(a % min_multiplo_comum != 0) {
                a += 1;
            }
            min_multiplo_comum = a;
        }
    }

    min_xy = min_multiplo_comum;
    resp = 1 + (b - min_xy) / aux_min;

    if (b - min_xy < 0) {
        resp = 0;
    }
    cout << resp;

    return 0;
}
