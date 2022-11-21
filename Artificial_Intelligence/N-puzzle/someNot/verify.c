// -------------------------------------------------------------
//
// This program verifies whether a solution is valid
// 
// Syntax:
// generator <problem> <solution> 
//
// Author: Ioannis Refanidis, January 2008
//         
// --------------------------------------------------------------

/*
 * By removing this comment the program will show the intermediate steps
 */
//#define _DEBUG

#include "auxiliary.h"

int puzzle[N][N];		// A puzzle

// Auxiliary function that displays a message in case of wrong input parameters.
void syntax_message()
{
	printf("Use syntax:\n\n");
	printf("\tverify <problem-file> <solution-file>\n\n");
	}


// This function checks whether a puzzle is a solution puzzle.
// Inputs:
//		int p[N][N]		: A puzzle
// Outputs:
//		1 --> The puzzle is a solution puzzle
//		0 --> The puzzle is NOT a solution puzzle
int is_solution(int p[N][N])
{
	int i,j;
	for(i=0;i<N;i++)
		for(j=0;j<N;j++)
			if (((i<N-1 || j<N-1) && p[i][j]!=i*N+j+1) ||(i==N-1 && j==N-1 && p[i][j]!=0))
				return 0;
	return 1;
}

// This function checks whether the solution file
// really containts a solution for the input problem.
// In case of success it returns 0, in case of any problem
// it returns -1.
int check_solution(char* filename)
{
	FILE *fin;
	int i,j,k,step,steps,err;
	char step_name[128];
	fin=fopen(filename, "r");
	if (fin==NULL)
	{
		printf("Cannot open file %s. Program terminates.\n",filename);
		return -1;
	}

	// read the number of steps
	err=fscanf(fin,"%d",&steps);
	if (err<1)
	{
		#ifdef _DEBUG
			printf("Cannot read number of steps. Program terminates.\n");
		#endif
		fclose(fin);
		return -1;
	}

	// find the position of the blank in the input file
	find_blank(puzzle,&i,&j);

	// for each step in the solution file
	for(k=1;k<=steps;k++)
	{
		err=fscanf(fin,"%s",step_name);
		if (err<1)
		{
			#ifdef _DEBUG
				printf("Cannot read step #%d. Program terminates.\n",k);
			#endif
			fclose(fin);
			return -1;
		}

		// "decode" the step
		if (strcmp(step_name,"left")==0)
			step=left;
		else if (strcmp(step_name,"right")==0)
			step=right;
		else if (strcmp(step_name,"up")==0)
			step=up;
		else if (strcmp(step_name,"down")==0)
			step=down;
		else
		{
			#ifdef _DEBUG
				printf("Unknown step string #%d. Program terminates.\n",k);
			#endif
			fclose(fin);
			return -1;
		}

		// for each type of step we check whether the particular move of the blank is valid
		switch (step)
		{
		case left:
			if(j>0)
			{
				puzzle[i][j]=puzzle[i][j-1];
				j--;
				puzzle[i][j]=0;
			}
			else
			{
				#ifdef _DEBUG
					printf("Error in step #%d. Blank cannot be moved to the left. Program terminates.\n",k); 
				#endif
				fclose(fin);
				return -1;
			}
			break;
		case right:
			if(j<N-1)
			{
				puzzle[i][j]=puzzle[i][j+1];
				j++;
				puzzle[i][j]=0;
			}
			else
			{
				#ifdef _DEBUG
					printf("Error in step #%d. Blank cannot be moved to the right. Program terminates.\n",k); 
				#endif
				fclose(fin);
				return -1;
			}
			break;
		case up:
			if(i>0)
			{
				puzzle[i][j]=puzzle[i-1][j];
				i--;
				puzzle[i][j]=0;
			}
			else
			{
				#ifdef _DEBUG
					printf("Error in step #%d. Blank cannot be moved up. Program terminates.\n",k); 
				#endif
				fclose(fin);
				return -1;
			}
			break;
		case down:
			if(i<N-1)
			{
				puzzle[i][j]=puzzle[i+1][j];
				i++;
				puzzle[i][j]=0;
			}
			else
			{
				#ifdef _DEBUG
					printf("Error in step #%d. Blank cannot be moved down. Program terminates.\n",k); 
				#endif
				fclose(fin);
				return -1;
			}
			break;
		}
	}

	fclose(fin);

	// Finally we check whether the input puzzle, after having applied alld the steps
	// described in the solution file, coincides with the solution.
	if (is_solution(puzzle))
		return 0;
	else
	{
		#ifdef _DEBUG
			printf("Not a valid solution.\n");
		#endif
		return -1;
	}
}

int main(int argc, char** argv)
{
//	char filename[100];
	int err;

	// Seed the random-number generator with current time so that
	// the numbers will be different every time we run.
	
	if (argc!=3)
	{
		printf("Wrong number of arguments. Use correct syntax:\n");
		syntax_message();
		return -1;
	}

	err=read_puzzle(argv[1], puzzle);
	if (err<0)
		return -1;
	
	err=check_solution(argv[2]);
	if (err==0)
		printf("OK!\n");
	else
		printf("error\n");
	return err;
}
