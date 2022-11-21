// -------------------------------------------------------------
//
// This program solves (N^2-1)-puzzles using four algorithms:
// - Depth first search
// - Breadth first search
// - Best first search
// - A*
// N is defined as a constant.
// Puzzles are read from an input file, while solution is written
// to an output file.
//
// Author: Ioannis Refanidis, January 2008
//
// --------------------------------------------------------------

/*
 * By removing this comment the program will show the intermediate steps
 */

#include "auxiliary.h"

#include <time.h>

#define breadth 1 // Constants denoting the four algorithms
#define depth 2
#define best 3
#define astar 4

// A 3x3 puzzle:
//=
//	+---+---+---+
//  |0,0|0,1|0,2|
//	+---+---+---+
//  |1,0|1,1|1,2|
//	+---+---+---+
//  |2,0|2,1|2,2|
//	+---+---+---+
//
// Two dimensional arrays are used to denote puzzles.
// The blank is denoted as 0, whereas positive numbers in the range 1 to N^2-1
// denote the tiles.
// Note the numbering of the cells, i.e. the upper left corner is the cell [0][0].
// Generally, in the solution the blank is considered to be in the lower right corner.
// For example, the solution of a 3x3 puzzle is the following:
//
//	+---+---+---+
//  | 1 | 2 | 3 |
//	+---+---+---+
//  | 4 | 5 | 6 |
//	+---+---+---+
//  | 7 | 8 |   |
//	+---+---+---+
//

// A node of the search tree. Note that the search tree is not binary,
// however we exploit the fact that each node of the tree has at most four children.




struct tree_node
{
	int p[N][N];
	int h;					  // the value of the heuristic function for this node
	int g;					  // the depth of this node wrt the root of the search tree
	int f;					  // f=0 or f=h or f=h+g, depending on the search algorithm used.
	struct tree_node *parent; // pointer to the parrent node (NULL for the root).
	int direction;			  // The direction of the last move
};

// A node of the frontier. Frontier is kept as a double-linked list,
// for efficiency reasons for the breadth-first search algorithm.
struct frontier_node
{
	struct tree_node *n;			// pointer to a search-tree node
	struct frontier_node *previous; // pointer to the previous frontier node
	struct frontier_node *next;		// pointer to the next frontier node
};

struct frontier_node *frontier_head = NULL; // The one end of the frontier
struct frontier_node *frontier_tail = NULL; // The other end of the frontier

clock_t t1;		   // Start time of the search algorithm
clock_t t2;		   // End time of the search algorithm
#define TIMEOUT 60 // Program terminates after TIMOUT secs

int solution_length; // The lenght of the solution table.
int *solution;		 // Pointer to a dynamic table with the moves of the solution.

// Reading run-time parameters.
int get_method(char *s)
{
	if (strcmp(s, "breadth") == 0)
		return breadth;
	else if (strcmp(s, "depth") == 0)
		return depth;
	else if (strcmp(s, "best") == 0)
		return best;
	else if (strcmp(s, "astar") == 0)
		return astar;
	else
		return -1;
}

// This function checks whether two puzzles are equal.
// Inputs:
//		p1[N][N]	: A puzzle
//		p2[N][N]	: A puzzle
// Output:
//		1 --> The puzzles are equal
//		0 --> The puzzles are not equal
int equal_puzzles(int p1[N][N], int p2[N][N])
{
	int i, j;
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
			if (p1[i][j] != p2[i][j])
				return 0;

	return 1;
}

// This function checks whether a node in the search tree
// holds exactly the same puzzle with at least one of its
// predecessors. This function is used when creating the childs
// of an existing search tree node, in order to check for each one of the childs
// whether this appears in the path from the root to its parent.
// This is a moderate way to detect loops in the search.
// Inputs:
//		struct tree_node *new_node	: A search tree node (usually a new one)
// Output:
//		1 --> No coincidence with any predecessor
//		0 --> Loop detection
int check_with_parents(struct tree_node *new_node)
{
	struct tree_node *parent = new_node->parent;
	while (parent != NULL)
	{
		if (equal_puzzles(new_node->p, parent->p))
			return 0;
		parent = parent->parent;
	}
	return 1;
}

// Giving a number n, this function returns the row
// of this number in a solution puzzle. For example,
// in a 3x3 puzzle, number 7 should appear in the row 2
// in the solution (note that the first row is numbered as 0).
// Inputs:
//		int n;	A number between 1 and N^2-1
// Output:
//		An integer indicating the vertical position of the number n in the solution.
int get_x(int n)
{
	return (n - 1) / N;
}

// Giving a number n, this function returns the column
// of this number in a solution puzzle. For example,
// in a 3x3 puzzle, number 7 should appear in the column 1
// in the solution (note that the first column is numbered as 0).
// Inputs:
//		int n;	A number between 1 and N^2-1
// Output:
//		An integer indicating the horizontal position of the number n in the solution.
int get_y(int n)
{
	return (n - 1) % N;
}

// Giving a number n and its position (i,j) in a puzzle,
// this function returns the manhattan distance of this number
// from its intended final position. For example,
// in a 3x3 puzzle, suppose that number 7 appears in position (1,2),
// then its manhattan distance is 2.
// Inputs:
//		int i;	The current vertical position of the number, 0<=i<N.
//		int j;	The current horizontal position of the number, 0<=j<N.
//		int n;	A number, 1<=n<N^2
// Output:
//		The manhattan distance between the current position of n and its intendet position.
int manhattan_distance(int i, int j, int n)
{
	int x, y;
	if (n == 0)
		return 0;
	x = get_x(n);
	y = get_y(n);
	return abs(i - x) + abs(j - y);
}

// Giving a puzzle, this function computes the sum of the manhattan
// distances between the current positions and the intended positions
// for all the tiles of the puzzle.
// Inputs:
//		int p[N][N];	A puzzle
// Output:
//		As described above.
int heuristic(int p[N][N])
{
	int i, j;
	int score = 0;
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
			score += manhattan_distance(i, j, p[i][j]);
	return score;
}

// This function adds a pointer to a new leaf search-tree node at the front of the frontier.
// This function is called by the depth-first search algorithm.
// Inputs:
//		struct tree_node *node	: A (leaf) search-tree node.
// Output:
//		0 --> The new frontier node has been added successfully.
//		-1 --> Memory problem when inserting the new frontier node .
int add_frontier_front(struct tree_node *node)
{
	// Creating the new frontier node
	struct frontier_node *new_frontier_node = (struct frontier_node *)
		malloc(sizeof(struct frontier_node));
	if (new_frontier_node == NULL)
		return -1;

	new_frontier_node->n = node;
	new_frontier_node->previous = NULL;
	new_frontier_node->next = frontier_head;

	if (frontier_head == NULL)
	{
		frontier_head = new_frontier_node;
		frontier_tail = new_frontier_node;
	}
	else
	{
		frontier_head->previous = new_frontier_node;
		frontier_head = new_frontier_node;
	}

#ifdef SHOW_COMMENTS
	printf("Added to the front...\n");
	display_puzzle(node->p);
#endif
	return 0;
}

// This function adds a pointer to a new leaf search-tree node at the back of the frontier.
// This function is called by the breadth-first search algorithm.
// Inputs:
//		struct tree_node *node	: A (leaf) search-tree node.
// Output:
//		0 --> The new frontier node has been added successfully.
//		-1 --> Memory problem when inserting the new frontier node .
int add_frontier_back(struct tree_node *node)
{
	// Creating the new frontier node
	struct frontier_node *new_frontier_node = (struct frontier_node *)malloc(sizeof(struct frontier_node));
	if (new_frontier_node == NULL)
		return -1;

	new_frontier_node->n = node;
	new_frontier_node->next = NULL;
	new_frontier_node->previous = frontier_tail;

	if (frontier_tail == NULL)
	{
		frontier_head = new_frontier_node;
		frontier_tail = new_frontier_node;
	}
	else
	{
		frontier_tail->next = new_frontier_node;
		frontier_tail = new_frontier_node;
	}

#ifdef SHOW_COMMENTS
	printf("Added to the back...\n");
	display_puzzle(node->p);
#endif

	return 0;
}

// This function adds a pointer to a new leaf search-tree node within the frontier.
// The frontier is always kept in increasing order wrt the f values of the corresponding
// search-tree nodes. The new frontier node is inserted in order.
// This function is called by the heuristic search algorithm.
// Inputs:
//		struct tree_node *node	: A (leaf) search-tree node.
// Output:
//		0 --> The new frontier node has been added successfully.
//		-1 --> Memory problem when inserting the new frontier node .
int add_frontier_in_order(struct tree_node *node)
{
	// Creating the new frontier node
	struct frontier_node *new_frontier_node = (struct frontier_node *)
		malloc(sizeof(struct frontier_node));
	if (new_frontier_node == NULL)
		return -1;

	new_frontier_node->n = node;
	new_frontier_node->previous = NULL;
	new_frontier_node->next = NULL;

	if (frontier_head == NULL)
	{
		frontier_head = new_frontier_node;
		frontier_tail = new_frontier_node;
	}
	else
	{
		struct frontier_node *pt;
		pt = frontier_head;

		// Search in the frontier for the first node that corresponds to either a larger f value
		// or to an equal f value but larger h value
		// Note that for the best first search algorithm, f and h values coincide.
		while (pt != NULL && (pt->n->f < node->f || (pt->n->f == node->f && pt->n->h < node->h)))
			pt = pt->next;

		if (pt != NULL)
		{
			// new_frontier_node is inserted before pt .
			if (pt->previous != NULL)
			{
				pt->previous->next = new_frontier_node;
				new_frontier_node->next = pt;
				new_frontier_node->previous = pt->previous;
				pt->previous = new_frontier_node;
			}
			else
			{
				// In this case, new_frontier_node becomes the first node of the frontier.
				new_frontier_node->next = pt;
				pt->previous = new_frontier_node;
				frontier_head = new_frontier_node;
			}
		}
		else
		{
			// if pt==NULL, new_frontier_node is inserted at the back of the frontier
			frontier_tail->next = new_frontier_node;
			new_frontier_node->previous = frontier_tail;
			frontier_tail = new_frontier_node;
		}
	}

#ifdef SHOW_COMMENTS
	printf("Added in order (f=%d)...\n", node->f);
	display_puzzle(node->p);
#endif

	return 0;
}

// This function expands a leaf-node of the search tree.
// A leaf-node may have up to 4 childs. A table with 4 pointers
// to these childs is created, with NULLs for those childrens that do not exist.
// In case no child exists (due to loop-detections), the table is not created
// and a 'higher-level' NULL indicates this situation.
// Inputs:
//		struct tree_node *current_node	: A leaf-node of the search tree.
// Output:
//		The same leaf-node expanded with pointers to its children (if any).
int find_children(struct tree_node *current_node, int method)
{
	int i, j, x, y;

	// Find the blank position in the current puzzle
	find_blank(current_node->p, &i, &j);

	// Move blank to the left
	if (j > 0)
	{
		int jj;
		// Initializing the new child
		struct tree_node *child = (struct tree_node *)malloc(sizeof(struct tree_node));
		if (child == NULL)
			return -1;

		child->parent = current_node;
		child->direction = left;
		child->g = current_node->g + 1; // The depth of the new child
		// Computing the puzzle for the new child
		for (x = 0; x < N; x++)
			for (y = 0; y < N; y++)
				if (x == i && y == j - 1)
					child->p[x][y] = 0;
				else if (x == i && y == j)
					child->p[x][y] = current_node->p[i][j - 1];
				else
					child->p[x][y] = current_node->p[x][y];

		// Check for loops
		if (!check_with_parents(child))
			// In case of loop detection, the child is deleted
			free(child);
		else
		{
			// Computing the heuristic value
			child->h = heuristic(child->p);
			if (method == best)
				child->f = child->h;
			else if (method == astar)
				child->f = child->g + child->h;
			else
				child->f = 0;

			int err = 0;
			if (method == depth)
				err = add_frontier_front(child);
			else if (method == breadth)
				err = add_frontier_back(child);
			else if (method == best || method == astar)
				err = add_frontier_in_order(child);
			if (err < 0)
				return -1;
		}
	}

	// Move blank to the right
	if (j < N - 1)
	{
		int jj;
		// Initializing the new child
		struct tree_node *child = (struct tree_node *)malloc(sizeof(struct tree_node));
		if (child == NULL)
			return -1;

		child->parent = current_node;
		child->direction = right;
		child->g = current_node->g + 1; // The depth of the new child
		// Computing the puzzle for the new child
		for (x = 0; x < N; x++)
			for (y = 0; y < N; y++)
				if (x == i && y == j + 1)
					child->p[x][y] = 0;
				else if (x == i && y == j)
					child->p[x][y] = current_node->p[i][j + 1];
				else
					child->p[x][y] = current_node->p[x][y];

		// Check for loops
		if (!check_with_parents(child))
		{
			// In case of loop detection, the child is deleted
			free(child);
			child = NULL;
		}
		else
		{
			// Computing the heuristic value
			child->h = heuristic(child->p);
			if (method == best)
				child->f = child->h;
			else if (method == astar)
				child->f = child->g + child->h;
			else
				child->f = 0;

			int err = 0;
			if (method == depth)
				err = add_frontier_front(child);
			else if (method == breadth)
				err = add_frontier_back(child);
			else if (method == best || method == astar)
				err = add_frontier_in_order(child);
			if (err < 0)
				return -1;
		}
	}

	// Move blank up
	if (i > 0)
	{
		int jj;
		// Initializing the new child
		struct tree_node *child = (struct tree_node *)malloc(sizeof(struct tree_node));
		if (child == NULL)
			return -1;

		child->parent = current_node;
		child->direction = up;
		child->g = current_node->g + 1; // The depth of the new child
		// Computing the puzzle for the new child
		for (x = 0; x < N; x++)
			for (y = 0; y < N; y++)
				if (x == i - 1 && y == j)
					child->p[x][y] = 0;
				else if (x == i && y == j)
					child->p[x][y] = current_node->p[i - 1][j];
				else
					child->p[x][y] = current_node->p[x][y];

		// Check for loops
		if (!check_with_parents(child))
		{
			// In case of loop detection, the child is deleted
			free(child);
			child = NULL;
		}
		else
		{
			// Computing the heuristic value
			child->h = heuristic(child->p);
			if (method == best)
				child->f = child->h;
			else if (method == astar)
				child->f = child->g + child->h;
			else
				child->f = 0;

			int err = 0;
			if (method == depth)
				err = add_frontier_front(child);
			else if (method == breadth)
				err = add_frontier_back(child);
			else if (method == best || method == astar)
				err = add_frontier_in_order(child);
			if (err < 0)
				return -1;
		}
	}

	// Move blank down
	if (i < N - 1)
	{
		int jj;
		// Initializing the new child
		struct tree_node *child = (struct tree_node *)malloc(sizeof(struct tree_node));
		if (child == NULL)
			return -1;

		child->parent = current_node;
		child->direction = down;
		child->g = current_node->g + 1; // The depth of the new child
		// Computing the puzzle for the new child
		for (x = 0; x < N; x++)
			for (y = 0; y < N; y++)
				if (x == i + 1 && y == j)
					child->p[x][y] = 0;
				else if (x == i && y == j)
					child->p[x][y] = current_node->p[i + 1][j];
				else
					child->p[x][y] = current_node->p[x][y];

		// Check for loops
		if (!check_with_parents(child))
		{
			// In case of loop detection, the child is deleted
			free(child);
			child = NULL;
		}
		else
		{
			// Computing the heuristic value
			child->h = heuristic(child->p);
			if (method == best)
				child->f = child->h;
			else if (method == astar)
				child->f = child->g + child->h;
			else
				child->f = 0;

			int err = 0;
			if (method == depth)
				err = add_frontier_front(child);
			else if (method == breadth)
				err = add_frontier_back(child);
			else if (method == best || method == astar)
				err = add_frontier_in_order(child);
			if (err < 0)
				return -1;
		}
	}

	return 1;
}

// Auxiliary function that displays a message in case of wrong input parameters.
void syntax_message()
{
	printf("puzzle <method> <input-file> <output-file>\n\n");
	printf("where: ");
	printf("<method> = breadth|depth|best|astar\n");
	printf("<input-file> is a file containing a %dx%d puzzle description.\n", N, N);
	printf("<output-file> is the file where the solution will be written.\n");
}

// This function checks whether a puzzle is a solution puzzle.
// Inputs:
//		int p[N][N]		: A puzzle
// Outputs:
//		1 --> The puzzle is a solution puzzle
//		0 --> The puzzle is NOT a solution puzzle
int is_solution(int p[N][N])
{
	int i, j;
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
			if (((i < N - 1 || j < N - 1) && p[i][j] != i * N + j + 1) || (i == N - 1 && j == N - 1 && p[i][j] != 0))
				return 0;
	return 1;
}

// Giving a (solution) leaf-node of the search tree, this function computes
// the moves of the blank that have to be done, starting from the root puzzle,
// in order to go to the leaf node's puzzle.
// Inputs:
//		struct tree_node *solution_node	: A leaf-node
// Output:
//		The sequence of blank's moves that have to be done, starting from the root puzzle,
//		in order to receive the leaf-node's puzzle, is stored into the global variable solution.
void extract_solution(struct tree_node *solution_node)
{
	int i;

	struct tree_node *temp_node = solution_node;
	solution_length = solution_node->g;

	solution = (int *)malloc(solution_length * sizeof(int));
	temp_node = solution_node;
	i = solution_length;
	while (temp_node->parent != NULL)
	{
		i--;
		solution[i] = temp_node->direction;
		temp_node = temp_node->parent;
	}
}

// This function writes the solution into a file
// Inputs:
//		char* filename	: The name of the file where the solution will be written.
// Outputs:
//		Nothing (apart from the new file)
void write_solution_to_file(char *filename, int solution_length, int *solution)
{
	int i;
	FILE *fout;
	fout = fopen(filename, "w");
	if (fout == NULL)
	{
		printf("Cannot open output file to write solution.\n");
		printf("Now exiting...");
		return;
	}
	fprintf(fout, "%d\n", solution_length);
	for (i = 0; i < solution_length; i++)
		switch (solution[i])
		{
		case left:
			fprintf(fout, "left\n");
			break;
		case right:
			fprintf(fout, "right\n");
			break;
		case up:
			fprintf(fout, "up\n");
			break;
		case down:
			fprintf(fout, "down\n");
			break;
		}
	fclose(fout);
}

// This function initializes the search, i.e. it creates the root node of the search tree
// and the first node of the frontier.
void initialize_search(int puzzle[N][N], int method)
{
	struct tree_node *root = NULL; // the root of the search tree.
	int i, j, jj;

	// Initialize search tree
	root = (struct tree_node *)malloc(sizeof(struct tree_node));
	root->parent = NULL;
	root->direction = -1;
	for (i = 0; i < N; i++)
		for (j = 0; j < N; j++)
			root->p[i][j] = puzzle[i][j];

	root->g = 0;
	root->h = heuristic(root->p);
	if (method == best)
		root->f = root->h;
	else if (method == astar)
		root->f = root->g + root->h;
	else
		root->f = 0;

	// Initialize frontier
	add_frontier_front(root);
}

// This function implements at the higest level the search algorithms.
// The various search algorithms differ only in the way the insert
// new nodes into the frontier, so most of the code is commmon for all algorithms.
// Inputs:
//		Nothing, except for the global variables root, frontier_head and frontier_tail.
// Output:
//		NULL --> The problem cannot be solved
//		struct tree_node*	: A pointer to a search-tree leaf node that corresponds to a solution.
struct tree_node *search(int method)
{
	clock_t t;
	int i, err;
	struct frontier_node *temp_frontier_node;
	struct tree_node *current_node;

	while (frontier_head != NULL)
	{
		t = clock();
		if (t - t1 > CLOCKS_PER_SEC * TIMEOUT)
		{
			printf("Timeout\n");
			return NULL;
		}

		// Extract the first node from the frontier
		current_node = frontier_head->n;
#ifdef SHOW_COMMENTS
		printf("Extracted from frontier...\n");
		display_puzzle(current_node->p);
#endif
		if (is_solution(current_node->p))
			return current_node;

		// Delete the first node of the frontier
		temp_frontier_node = frontier_head;
		frontier_head = frontier_head->next;
		free(temp_frontier_node);
		if (frontier_head == NULL)
			frontier_tail = NULL;
		else
			frontier_head->previous = NULL;

		// Find the children of the extracted node
		int err = find_children(current_node, method);

		if (err < 0)
		{
			printf("Memory exhausted while creating new frontier node. Search is terminated...\n");
			return NULL;
		}
	}

	return NULL;
}

int main(int argc, char **argv)
{
	int err;
	struct tree_node *solution_node;
	int puzzle[N][N]; // The initial puzzle read from a file
	int method;		  // The search algorithm that will be used to solve the puzzle.

	if (argc != 4)
	{
		printf("Wrong number of arguments. Use correct syntax:\n");
		syntax_message();
		return -1;
	}

	method = get_method(argv[1]);
	if (method < 0)
	{
		printf("Wrong method. Use correct syntax:\n");
		syntax_message();
		return -1;
	}

	err = read_puzzle(argv[2], puzzle);
	if (err < 0)
		return -1;

	printf("Solving %s using %s...\n", argv[2], argv[1]);
	t1 = clock();

	initialize_search(puzzle, method);

	solution_node = search(method); // The main call

	t2 = clock();

	if (solution_node != NULL)
		extract_solution(solution_node);
	else
		printf("No solution found.\n");

	if (solution_node != NULL)
	{
		printf("Solution found! (%d steps)\n", solution_length);
		printf("Time spent: %f secs\n", ((float)t2 - t1) / CLOCKS_PER_SEC);
		write_solution_to_file(argv[3], solution_length, solution);
	}

	return 0;
}
