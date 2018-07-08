#include <iostream>
#include <cstring>
using namespace std;

int main(){
    int count=0, n, m, ai[200500]={0},a;
    char st[200500];
    cin >> st;
    cin >> m;
    for (;cin >> a;)
        ai[a - 1]++;
    n = strlen(st);
    for (int i = 0; i < n / 2; i++) {
        count += ai[i];
        count = count & 1;
        if (count == 1) {
            swap(st[i], st[n - i - 1]);
        }
    }
    cout << st << endl;
    return 0;
}
