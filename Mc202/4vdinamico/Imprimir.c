void imprimir(Tinfo *info, char **vetor) { 

	int i;
	for(i=0; i<info->tam; i++) {
		printf("[%d] >>>%s<<<", i, vetor[i]);
		if(vetor[i]!=NULL)
			printf(" [%ld] ", strlen(vetor[i]));
		else
			printf(" [0] ");
		if(i==info->l)
			printf("   <-  info->l");
		if(i==info->f) 
			printf("   <-  info->f");
		if(i==info->passadoL) 
			printf("   <-  info->passadoL");
		if(i==info->passadoF) 
			printf("   <-  info->passadoF");
		printf("\n");
	}
}
