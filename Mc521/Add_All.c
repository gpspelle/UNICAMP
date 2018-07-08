#include <stdio.h>

long long int particao(long long int v[], long long int esq, long long int dir) {
	
	long long int i, fim;
	void troca(long long int v[],long long int i,long long int j);
	
	troca(v,esq,(esq+dir)/2);
	
	fim=esq;
	
	for(i=esq+1;i<=dir;i++)
		if(v[i]<v[esq]) troca(v,++fim,i);
			troca(v,esq,fim);
	
	return fim;
}
void quicksort(long long int v[], long long int esq, long long int dir) {
  
	long long int i;
	if(esq>=dir) 
		return;
  
	i=particao(v,esq,dir);
	quicksort(v,esq,i-1);
	quicksort(v,i+1,dir);

}
void troca(long long int v[], long long int i, long long int j) {  
	
	long long int temp;
	
	temp=v[i];
	v[i]=v[j];
	v[j]=temp;
}

int main(void) {

    long long int N; 
    while(1) {
        scanf(" %lld", &N);
        if(N == 0) {
            break;
        }
        long long int v[6000], i, j;

        for(i = 0; i < N; i++) {
            scanf(" %lld", &v[i]);
        }

        long long int total = 0;
        long long int OLD_N = N;
        for(i = 1; i < OLD_N; i++) {
            quicksort(v, 0, N-1);
            v[0] += v[1]; 
            for(j = 1; j < N-1; j++) {
                v[j] = v[j+1];
            }
            N--;
            total += v[0];
        }

        printf("%lld\n", total);
            
    }
    
    return 0;
}
