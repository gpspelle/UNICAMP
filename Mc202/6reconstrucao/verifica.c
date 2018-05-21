/* program to construct tree using inorder and preorder traversals */
#include<stdio.h>
#include<stdlib.h>
 
/* A binary tree node has data, pointer to left child
   and a pointer to right child */
struct node
{
  char data;
  struct node* left;
  struct node* right;
};
 
int search(char arr[], int strt, int end, char value);

struct node* newNode(char data);
 
struct node* buildTree(char in[], char pre[], int inStrt, int inEnd)
{
  static int preIndex = 0;
  if(inStrt > inEnd)
     return NULL;
 
  struct node *tNode = newNode(pre[preIndex++]);
 

  if(inStrt == inEnd)
    return tNode;
 
  printf("preIndex: %d\n", preIndex);
  int inIndex = search(in, inStrt, inEnd, tNode->data);
 
  tNode->left = buildTree(in, pre, inStrt, inIndex-1);
  tNode->right = buildTree(in, pre, inIndex+1, inEnd);
 
  return tNode;
}
 
int search(char arr[], int strt, int end, char value) {
  int i;
  
  for(i = strt; i <= end; i++)
    if(arr[i] == value)
      return i;
      
  return i;
}
 
struct node* newNode(char data)
{
  struct node* node = (struct node*)malloc(sizeof(struct node));
  node->data = data;
  node->left = NULL;
  node->right = NULL;
 
  return(node);
}
 
/* This funtcion is here just to test buildTree() */
void printInorder(struct node* node)
{
  if (node == NULL)
     return;
 
  /* first recur on left child */
  printInorder(node->left);
 
  /* then print the data of node */
  printf("%c ", node->data);
 
  /* now recur on right child */
  printInorder(node->right);
}
void imprimir(struct node *raiz) 
{ 

	if(raiz==NULL)
		return;	
		
	imprimir(raiz->left);
	imprimir(raiz->right);
	
	printf("%c", raiz->data);
	
}
/* Driver program to test above functions */
int main()
{
  char in[] = {'D', 'B', 'E', 'A', 'F', 'C'};
  char pre[] = {'A', 'B', 'D', 'E', 'C', 'F'};
  int len = sizeof(in)/sizeof(char);
  struct node *root = buildTree(in, pre, 0, len - 1);

  imprimir(root);
  printf("\n");
  
}
