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
   	
	vetor[0][info->f] = (char *)malloc(sizeof(char)*strlen(palavra));
	strcpy(vetor[0][info->f], palavra);
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
	imprimir(info, *vetor);
}	
