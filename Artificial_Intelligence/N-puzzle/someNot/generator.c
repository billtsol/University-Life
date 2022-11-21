// -------------------------------------------------------------
//
// This program creates instances of (N^2-1)-puzzles
// Instances are created radomly, however a check is performed
// after creation in order to ensure that the instance is solvable.
// Non-solvable instances are ignored.
// Instances are named from <prefix><id1>.txt to <prefix><id2>.txt .
//
// Syntax:
// generator <prefix> <id1> <id2>
//
// Author: Ioannis Refanidis, January 2008
//
// --------------------------------------------------------------

#include "auxiliary.h"

int puzzle[N][N]; // A puzzle

// This function creates radomly a new puzzle.
void new_puzzle()
{
	int i, j, d, found;

	int flags[N * N];
	for (i = 0; i < N * N; i++)
		flags[i] = 0;

	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
		{
			found = 0;
			do
			{
				d = ((double)rand() / ((int)RAND_MAX + 1.0)) * (N * N);
				if (flags[d] == 0)
				{
					flags[d] = 1;
					puzzle[i][j] = d;
					found = 1;
				}
			} while (found == 0);
		}
}

// Auxiliary function that displays a message in case of wrong input parameters.
void syntax_message()
{
	printf("Use syntax:\n\n");
	printf("\tgenerator <prefix> <id1> <id2>\n\n");
	printf("where:\n ");
	printf("\t<prefix> = the prefix of the filename of the instances to be generated\n");
	printf("\t<id1> = a number indicating the suffix of the first instance.\n");
	printf("\t<id2> = a number indicating the suffix of the last instance.\n\n");
	printf("e.g. the call \n\n\tgenerator test 1 10\n\ngenerates 10 instances with names ranging from test1.txt to test10.txt.\n");
	printf("Constraints: id1>0, id2>0, id1<=id2.");
}

// This function serializes a puzzle. For example, if the input puzzle is:
//
//		+---+---+---+
//      | 1 | 2 | 3 |
//		+---+---+---+
//      | 4 | 5 | 6 |
//		+---+---+---+
//      | 7 | 8 | 0 |
//		+---+---+---+
//
// then its serialized version is the following:
// 1 2 3 6 5 4 7 8 0
//
void serialize(int p[N][N], int serial_p[N * N])
{
	int i, j, x, y, z;

	x = 0;
	y = 0;
	z = 0;
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
		{
			serial_p[z] = p[x][y];
			z++;
			if (x % 2 == 0)
			{
				if (y < N - 1)
					y++;
				else
					x++;
			}
			else
			{
				if (y > 0)
					y--;
				else
					x++;
			}
		}
}

// This function checks whether a appears before b in the serial_solution table.
int correct_order(int a, int b, int serial_solution[N * N])
{
	int i;
	for (i = 0; i < N * N; i++)
		if (serial_solution[i] == a)
			return 1;
		else if (serial_solution[i] == b)
			return 0;

	printf("Error in the program. Now terminating...\n");
	exit(-1);
}

// This function checks whether a specific arrangement of the tiles in the puzzle
// can be solved. Given p[N][N], in order to check this in linear time (wrt N*N)
// whether p is a solvable instance, we use the following algorithm:
// - Step 1: Serialize p
// - Step 2: Serialize a solution puzzle
// - Step 3: Count how many pairs of numbers (excluding pairs with 0) appear in reverse order (swap)
//           in the serial version of p wrt serial version of the solution.
// - Step 4: If the number of swaps is an even number, p is a solvable instance, otherwise it is not.
int can_be_solved(int p[N][N])
{
	int i, j;
	int serial_p[N * N];
	int solution[N][N];
	int serial_solution[N * N];
	int swaps;

	// Create a solution puzzle
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
			if (i < N - 1 || j < N - 1)
				solution[i][j] = i * N + j + 1;
			else
				solution[i][j] = 0;

	// Serialize the two puzzles
	serialize(p, serial_p);
	serialize(solution, serial_solution);

	// Count swaps
	swaps = 0;
	for (i = 0; i < N * N - 1; i++)
		for (j = i + 1; j < N * N; j++)
			if (serial_p[i] > 0 && serial_p[j] > 0)
				if (!correct_order(serial_p[i], serial_p[j], serial_solution))
					swaps++;

	if (swaps % 2 == 0)
		return 1;
	else
		return 0;
}

void number2string(int n, char *s)
{
	int i = 0, j;
	while (n > 0)
	{
		// shift one position to the right
		for (j = i - 1; j >= 0; j--)
			s[j + 1] = s[j];
		s[0] = (char)(n % 10) + '0';
		n = n / 10;
		i++;
	}
	s[i] = '\0';
}

// This function writes the puzzle to a file
void write_to_file(int id, char *filename)
{
	int i, j;
	char *temp = (char *)malloc(10 * sizeof(char));
	FILE *fout;
	char s[255];
	strcpy(s, filename);

	number2string(id, temp);
	strcat(s, temp);
	strcat(s, ".txt");

	fout = fopen(s, "w");

	for (i = 0; i < N; i++)
	{
		for (j = 0; j < N; j++)
			fprintf(fout, "%d\t", puzzle[i][j]);
		fprintf(fout, "\n");
	}

	fclose(fout);
}

int main(int argc, char **argv)
{
	long id1, id2, i;
	char *endPtr;

	// Seed the random-number generator with current time so that
	// the numbers will be different every time we run.

	// srand((unsigned)time(NULL));

	if (argc != 4)
	{
		printf("Wrong number of arguments. Use correct syntax:\n");
		syntax_message();
		return -1;
	}

	id1 = strtol(argv[2], &endPtr, 10);
	id2 = strtol(argv[3], &endPtr, 10);
	if (id1 <= 0 || id2 <= 0 || id1 > id2)
	{
		syntax_message();
		return -1;
	}

	for (i = id1; i <= id2; i++)
	{
		do
		{
			new_puzzle();
			if (is_valid(puzzle) == 0)
			{
				printf("There is a bug in the generator... Program terminates.\n");
				return -1;
			}
		} while (!can_be_solved(puzzle));
		write_to_file(i, argv[1]);
	}

	return 0;
}
