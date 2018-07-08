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

void acha_componente (matriz &amizades, vetor &componente, lli i) {
    lli j;
    for (j = 0; j < amizades[i].size(); j++) {
        if (amizades[i][j] == 1 && componente[j] == 0) {
            componente[j] = 1;
            acha_componente (amizades, componente, j);
        }

    }
}

int main () {
    lli pessoas, n_amizades, i, j, a, b;

    cin >> pessoas >> n_amizades;

    vetor dividas (pessoas, 0);
    for (i = 0; i < pessoas; i++) {
        cin >> dividas[i];
    }
    matriz amizades;
    for (i = 0; i < pessoas; i++) {
        vetor aux (pessoas, 0);
        amizades.push_back (aux);
    }

    for (i = 0; i < n_amizades; i++) {
        cin >> a >> b;
        amizades[a][b] = 1;
        amizades[b][a] = 1;
    }
    /*
    for (i = 0; i < pessoas; i++)
        cout << dividas[i] << endl;

    for (i = 0; i < pessoas; i++) {
        for (j = 0; j < pessoas; j++) {
            cout << amizades[i][j] << " ";
        }
        cout << endl;
    }
    */
    vetor verificado (pessoas, 0);
    lli flag = 1;
    for (i = 0; i < pessoas && flag == 1; i++) {
        if (verificado[i] == 0) {
            vetor componente (pessoas, 0);
            componente[i] = 1;
            acha_componente (amizades, componente, i);
            lli total = 0;
            for (j = 0; j < pessoas; j++) {
                if (componente[j] == 1) {
                    total += dividas[j];
                    verificado[j] = 1;
                }
            }
            if (total != 0)
                flag = 0;
        }
    }
    if (flag == 1)
        cout << "POSSIBLE" << endl;
    else
        cout << "IMPOSSIBLE" << endl;

    return 0;
}

