#include <cstdio>
#include <iostream>
#include <algorithm>
#include <cmath>
#include <stdlib.h>
#include <string>
#include <vector>
#include <deque>
#include <map>
#include <queue>

using namespace std;

typedef long long int lli;
typedef vector <lli> vetor;
typedef vector <vector <lli> > matriz;

int main () {
    lli test_cases, n, k, resp;

    cin >> test_cases;
    while (test_cases > 0) {
        cin >> n >> k;
        resp = k ^ (k >> 1);
        cout << resp << endl;
        test_cases--;
    }
    return 0;
}

