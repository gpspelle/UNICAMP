#include <stdio.h>

int prim_p[6250000];
int segu_p[6250000];

int particao(int v[],int esq,int dir) {
	
	int i, fim;
	void troca(int v[],int i,int j);
	
	troca(v,esq,(esq+dir)/2);
	
	fim=esq;
	
	for(i=esq+1;i<=dir;i++)
		if(v[i]<v[esq]) troca(v,++fim,i);
			troca(v,esq,fim);
	
	return fim;
}

void quicksort(int v[], int esq, int dir) {
  
	int i;
	if(esq>=dir) 
		return;
  
	i=particao(v,esq,dir);
	quicksort(v,esq,i-1);
	quicksort(v,i+1,dir);

}

void troca(int v[], int i,int j) {  
	
	int temp;
	
	temp=v[i];
	v[i]=v[j];
	v[j]=temp;
}

int do_it(int x, int *index, int *segu_p) {
    int occur = 0;
    int l = 0, r = *index-1, mid;

    /* binary search for it */
    while(r > l) {
        mid = (l+r) / 2;
        if(segu_p[mid] >= x) {
            r = mid;
        }
        else { 
            l = mid+1;
        }
    }

    /* get all the possibilities */
    while(segu_p[l] == x && l < *index) {
        occur++;
        l++;
    }

    return occur;
}

int main(void) {
    int n, i, j, index = 0;
    long long int x = 0;
    int v1[2500][4];

    scanf(" %d", &n);
    
    for(i = 0; i < n; i++) {
        for(j = 0; j < 4; j++) {
            scanf("%d", &v1[i][j]);
        }
    }

    index = 0;
    for(i = 0; i < n; i++) {
        for(j = 0; j < n; j++) { 
            prim_p[index] = v1[i][0] + v1[j][1];
            index++;
        }
    }

    quicksort(prim_p, 0, index-1);

    index = 0;

    for(i = 0; i < n; i++) {
        for(j = 0; j < n; j++) {
            segu_p[index++] = v1[i][2] + v1[j][3];
        }
    }

    quicksort(segu_p, 0, index-1);

    for(i = 0; i < index; i++) {
        x += do_it(-prim_p[i], &index, segu_p);
    }

    printf("%lld\n", x);
    return 0;
}

