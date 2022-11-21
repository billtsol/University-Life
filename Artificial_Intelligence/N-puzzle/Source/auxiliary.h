#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define N	3		// The length of the puzzle's edge.

#define left	0		// Constants denoting the four directions
#define right	1		// in which the blank can be moved.
#define up	    2
#define down	3

// Auxiliary function that displays a puzzle on the screen (without lines).
void display_puzzle(int p[N][N])
{
	int i,j;
	for(i=0;i<N;i++)
	{
		for(j=0;j<N;j++)
			printf("%d\t",p[i][j]);
		printf("\n");
	}
}

// Auxiliary function that checks whether a puzzle is valid.
int is_valid(int p[N][N])
{
	int i,j,flag[N*N];
	for(i=0;i<N*N;i++)
		flag[i]=0;

	for(i=0;i<N;i++)
		for(j=0;j<N;j++)
		{
			if (p[i][j]<0 || p[i][j]>=N*N)		// Checks whether numbers are within bounds
				return 0;
			if (flag[p[i][j]]==1)				// Checks whether a number appears twice
				return 0;
			else
				flag[p[i][j]]=1;
		}
	return 1;
}

// This function reads a file containing a puzzle and stores the numbers
// in the global variable int puzzle[N][N].
// Inputs:
//		char* filename	: The name of the file containing a NxN puzzle.
// Output:
//		0 --> Successful read.
//		1 --> Unsuccessful read
int read_puzzle(char* filename, int puzzle[N][N])
{
	FILE *fin;
	int i,j,err;
	fin=fopen(filename, "r");
	if (fin==NULL)
	{
		#ifdef SHOW_COMMENTS
			printf("Cannot open file %s. Program terminates.\n",filename);
		#endif
		return -1;
	}
	for (i=0;i<N;i++)
		for(j=0;j<N;j++)
		{
			err=fscanf(fin,"%d",&puzzle[i][j]);
			if (err<1)
			{
				#ifdef SHOW_COMMENTS
					printf("Cannot read item [%d][%d] of the puzzle. Program terminates.\n",i,j);
				#endif
				fclose(fin);
				return -1;
			}
		}
	fclose(fin);

	if (is_valid(puzzle))
		return 0;
	else
	{
		#ifdef SHOW_COMMENTS
			printf("Invalid puzzle contained in file %s. Program terminates.\n",filename);
		#endif
		return -1;
	}
}

// This function finds the position of the blank within a puzzle.
// Inputs:
//		p[N][N]	: A puzzle
//		int *i	: The memory address where the x-position of the blank will be written.
//		int *j	: The memory address where the y-position of the blank will be written.
void find_blank(int p[N][N], int *i, int *j)
{
	for(*i=0;*i<N;(*i)++)
		for(*j=0;*j<N;(*j)++)
			if (p[*i][*j]==0)
				return;
	*i=-1;
	*j=-1;
}

