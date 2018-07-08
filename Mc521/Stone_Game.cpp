#include <bits/stdc++.h>

using namespace std;

int main() {
    int n;
    while (true) {
        cin >> n;
        if (n == 0) break;
        int acc = 0;
        for (int i = 0; i < n; i++) {
            int x;
            cin >> x;
            acc ^= x;
        }
        if (acc)
            cout << "Yes\n";
        else
            cout << "No\n";
    }
}

