from typing import List

def dfs(matrix: List[List[int]], n: int, src: int, dst: int) -> None:
    """
    Perform Depth-First Search on a graph represented by an adjacency matrix.

    Parameters:
    - matrix (List[List[int]]): The adjacency matrix representing the graph.
    - n (int): The number of vertices in the graph.
    - src (int): The source node for the DFS traversal.
    - dst (int): The destination node to search for.

    Returns:
    - None: Prints the DFS traversal and whether the destination is reachable.
    """
    stack = []
    visited = [False] * n

    stack.append(src)
    visited[src] = True

    while stack:
        current = stack.pop()
        print(current, end=" ")

        if current == dst:
            print("\nDestination found!")
            return

        for i in range(n):
            if matrix[current][i] == 1 and not visited[i]:
                stack.append(i)
                visited[i] = True

    print("\nDestination not reachable from the source.")

def get_valid_input(prompt: str, min_value: int, max_value: int) -> int:
    """
    Get user input within a specified range.

    Parameters:
    - prompt (str): The prompt to display to the user.
    - min_value (int): The minimum allowed input value.
    - max_value (int): The maximum allowed input value.

    Returns:
    - int: The user-input value within the specified range.
    """
    value = -1

    while value < min_value or value > max_value:
        value = int(input(prompt))
        if value < min_value or value > max_value:
            print("Invalid input. Try again.")

    return value

def populate_matrix(matrix: List[List[int]], n: int) -> None:
    """
    Populate an adjacency matrix based on user input.

    Parameters:
    - matrix (List[List[int]]): The adjacency matrix to be populated.
    - n (int): The number of vertices in the graph.

    Returns:
    - None: Modifies the matrix based on user input.
    """
    while True:
        n1 = int(input("\nEnter the x - vertex (Enter -1 to break): "))
        if n1 == -1:
            break

        n2 = int(input("Enter the y - vertex: "))

        if is_valid_vertex(n1, n2, n):
            matrix[n1][n2] = 1
            matrix[n2][n1] = 1
        else:
            print("Invalid vertices. Try again.")

def is_valid_vertex(n1: int, n2: int, n: int) -> bool:
    """
    Check if the given vertex coordinates are valid.

    Parameters:
    - n1 (int): The x-coordinate of the vertex.
    - n2 (int): The y-coordinate of the vertex.
    - n (int): The number of vertices in the graph.

    Returns:
    - bool: True if the vertex coordinates are valid, False otherwise.
    """
    return 0 <= n1 < n and 0 <= n2 < n

def main() -> None:
    """
    Main function to execute the DFS algorithm on a user-defined graph.
    """
    n = get_valid_input("Enter the number of elements: ", 1, float('inf'))

    matrix = [[0] * n for _ in range(n)]

    populate_matrix(matrix, n)

    src = get_valid_input("\nEnter the source node: ", 0, n - 1)
    dst = get_valid_input("Enter the destination node: ", 0, n - 1)

    dfs(matrix, n, src, dst)

if __name__ == "__main__":
    main()


'''
Enter the number of elements: 5

Enter the x - vertex (Enter -1 to break): 0
Enter the y - vertex: 1

Enter the x - vertex (Enter -1 to break): 0
Enter the y - vertex: 2

Enter the x - vertex (Enter -1 to break): 1
Enter the y - vertex: 3

Enter the x - vertex (Enter -1 to break): 1
Enter the y - vertex: 4

Enter the x - vertex (Enter -1 to break): -1

Enter the source node: 0
Enter the destination node: 4
0 2 1 4 
Destination found!
'''