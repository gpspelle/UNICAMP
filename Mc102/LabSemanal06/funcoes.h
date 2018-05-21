//PRIMEIRAMENTE, DEFININDO ALGUMAS ESTRUTURAS UTILIZADAS 
typedef char string[31];
typedef struct {
	string nomeFuncionario;
	int produtividade;
	string boss;
	double nivelHierarquico;
	int presidente;
	double produtividadeRel;
	double salario; 
	double qualidade;
	}dados;

void zera_struct(dados *pointerFuncionario);
void pega_nome_produtividade(int numFuncionarios, dados *pointerFuncionario);
void pega_nome_chefe(int numFuncionarios, dados *pointerFuncionario);
double calcula_hierarquia(int numFuncionarios, dados *pointerFuncionario, int i);
void descobre_hierarquia(int numFuncionarios, dados *pointerFuncionario);
double calcula_relativa(int numFuncionarios, int i, dados *pointerFuncionario);
void produtividade_relativa(int numFuncionarios, dados *pointerFuncionario);
double fracao_salario(int numFuncionarios, dados *pointerFuncionario, int i);
void salario(int numFuncionarios, dados *pointerFuncionario);
void calcula_salario(int numFuncionarios, dados *pointerFuncionario, double soma, double gasto);
void soma_salario(int numFuncionarios, dados *pointerFuncionario, double gasto);
double calcula_log(dados *pointerFuncionario, int j);
double calcula_qualidade(dados *pointerFuncionario, double fator, int j);
void qualidade(int numFuncionarios, dados *pointerFuncionario, double fator);
int maior_qualidade(int numFuncionarios, dados *pointerFuncionario);
