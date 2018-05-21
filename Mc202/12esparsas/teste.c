for(i=0; i<k;) {
		j=i;
		guarda = linha[i];
		if(j>k)
			break;
		linha_true[++count] = j;	
		while(linha[j]==linha[j+1] && j+1<k)
			j++;	
		azar(valor, coluna, i, j);
		i=j+1;
		if(i>k)
			break;
			
		while(guarda+1<linha[i]) {
			guarda++;
			linha_true[++count] = -1;
		}
	}
