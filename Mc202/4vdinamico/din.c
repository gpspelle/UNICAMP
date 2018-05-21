#include "din.h"

void shift(Tinfo *info, char ***vetor) {

	int i, j;
	
	if (info->ocup!=0) {
		info->ocup--;
		info->f = info->passadoF;
		vetor[0][info->f]=NULL;
		if(info->ocup==0) {
			info->passadoF = -1;
			info->passadoL = -1;
		}
		else	
			info->passadoF = (info->passadoF + 1 )%info->tam;
	}	
	if(info->ocup==(info->tam)/(4) && info->ocup!=1) { 
		
		char *auxiliar[(info->tam)/4];
		for(j=0, i = info->passadoF; j<=info->ocup; i = (info->passadoF +1)%(info->tam) + j, j++)
			auxiliar[j] = vetor[0][i];
		vetor[0] = realloc(vetor[0], sizeof(char *)*(info->tam)/2);
		for(i=0; i<(info->tam)/4; i++)
			vetor[0][i] = auxiliar[i];

		info->tam = info->tam/2;
		info->passadoF = 0;
		info->passadoL = ((info->tam)/(2) - 1);
		info->f = info->tam-1; 
		info->l = info->tam/2;
		for(i=info->l; i<=info->f; i++)
			vetor[0][i]=NULL;
	}
}
void print_first(Tinfo *info, char ***vetor) {
	if(info->ocup!=0)
		printf("%s\n", vetor[0][info->passadoF]);
}

void push(Tinfo *info, char ***vetor) { 

	int i, j;
	char *palavra = (char *)malloc(sizeof(char)*5001);
	char c;
	scanf("%[^\n]s", palavra);	
	c = palavra[0];
	while(c==' ') { 
		for(i=0; i<=strlen(palavra); i++)
			palavra[i] = palavra[i+1]; 
		c=palavra[0];
	}
	char* tira = palavra + strlen(palavra);
    while(*--tira == ' ');
   	 *(tira + 1) = '\0';
    
	vetor[0][info->l] = palavra;
	info->ocup++;

	if(info->ocup==info->tam) { 
	
	
		char *auxiliar[info->tam];
		for(j=0, i=info->passadoF; j<info->tam; i = (i +1)%info->tam, j++)
			auxiliar[j] = vetor[0][i];
		
		vetor[0] = realloc(vetor[0], sizeof(char *)*2*(info->tam));
	
		for(i=0; i<info->tam; i++) 
			vetor[0][i] = auxiliar[i];
	
		info->tam = (info->tam)*2;
		info->f = (info->tam-1);
	
		info->passadoL = ((info->tam)/2 - 1);
		info->l = ((info->tam/2));
		info->passadoF = 0;	
	
	}
	else {
		if(info->ocup==1)
			info->passadoF = info->l;  
		info->passadoL = info->l;
		info->l = (info->l +1)%info->tam;
	}
}
void pop(Tinfo *info, char ***vetor) {
	
	int i, j;
	
	if (info->ocup!=0) {
		info->ocup--;
		vetor[0][info->passadoL]=NULL;
		info->l = info->passadoL;
		if(info->ocup==0) {
			info->passadoL = -1;
			info->passadoF = -1;
		}
		else 
			info->passadoL = (info->passadoL - 1 + info->tam)%info->tam;
	}	
	
	if(info->ocup==(info->tam)/(4) && info->ocup!=1) { 
		char *auxiliar[(info->tam)/4];
		
		for(j=0, i = info->passadoF; j<=info->ocup; i = (info->passadoF +1)%(info->tam) + j, j++)
			auxiliar[j] = vetor[0][i];
		
		vetor[0] = realloc(vetor[0], sizeof(char *)*(info->tam)/2);
		
		for(i=0; i<(info->tam)/4; i++)
			vetor[0][i] = auxiliar[i];
		info->tam = (info->tam)/2;

		info->passadoL = ((info->tam)/2)-1; 
		info->passadoF = 0; 
		info->f = info->tam-1; 
		info->l = (info->tam/2);
		for(i=info->l; i<=info->f; i++)
			vetor[0][i]=NULL;
	}
}
void print_last(Tinfo *info, char ***vetor) {
	if(info->ocup!=0)
		printf("%s\n", vetor[0][info->passadoL]);

}
void is_empty(Tinfo *info, char ***vetor) {
	if(info->ocup==0)
		printf("yep\n");
	else
		printf("nope\n");
}
void unshift(Tinfo *info, char ***vetor) { 
	
	int i, j;
	char *palavra = (char *)malloc(sizeof(char)*5001);
	char c;
	scanf("%[^\n]s", palavra);
	c = palavra[0];
	while(c==' ') { 
		for(i=0; i<=strlen(palavra); i++)
			palavra[i] = palavra[i+1]; 
		c=palavra[0];
	}
	char* tira = palavra + strlen(palavra);
    while(*--tira == ' ');
   	 *(tira + 1) = '\0';
   	
	vetor[0][info->f] = palavra;
	info->ocup++;	
	
	if(info->ocup==info->tam) { 
		char *auxiliar[info->tam];
		
		for(j=0, i=info->f; j<info->tam; i = (i +1)%info->tam, j++)
			auxiliar[j] = vetor[0][i];
			
		vetor[0] = realloc(vetor[0], sizeof(char *)*2*(info->tam));
		
		for(i=0; i<info->tam; i++) 
			vetor[0][i] = auxiliar[i];
		
		info->tam = (info->tam)*2;
		info->f = (info->tam-1);
		
		info->passadoL = ((info->tam)/2 - 1);
		info->l = (info->tam/2);
		info->passadoF = 0;	
		for(i=info->l; i<=info->f; i++)
			vetor[0][i]=NULL;
		
	}
	else {
		info->passadoF = info->f;
		if(info->ocup==1)
			info->passadoL = info->f;  
		info->f = (info->f - 1 + info->tam)%info->tam;
	}
}	
void opcao(Tinfo *info, char ***vetor) { 

	comando c; 
	while(1) { 
		scanf(" %s", c);
		if(!strcmp("unshift", c)) {   
			*c=getchar();
			if(*c!='\n')
				unshift(info, vetor);
		}
		else if(!strcmp("shift", c))       
			shift(info, vetor);
		else if(!strcmp("print-first", c)) 
			print_first(info, vetor);		
		else if(!strcmp("push", c)) {
		    *c=getchar();
			if(*c!='\n')
				push(info, vetor);
		}    
		else if(!strcmp("pop", c))         
			pop(info, vetor);	
		else if(!strcmp("print-last", c))  
			print_last(info, vetor);
		else if(!strcmp("is-empty", c))    
			is_empty(info, vetor);
		else if(!strcmp("exit", c)) {
			break;
		}
	}
}
