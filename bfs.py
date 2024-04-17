from collections import deque

def bfs(matrix: list[list[int]], n: int, src: int, dst: int) -> None:
    queue = deque()
    visited = [False] * n

    queue.append(src)
    visited[src] = True

    while queue:
        current = queue.popleft()
        print(current, end=" ")

        if current == dst:
            print("\nDestination found!")
            return

        for i in range(n):
            if matrix[current][i] == 1 and not visited[i]:
                queue.append(i)
                visited[i] = True

    print("\nDestination not reachable from the source.")

def get_valid_input(prompt: str, min_value: int, max_value: int) -> int:
    value = -1

    while value < min_value or value > max_value:
        value = int(input(prompt))
        if value < min_value or value > max_value:
            print("Invalid input. Try again.")

    return value

def populate_matrix(matrix: list[list[int]], n: int) -> None:
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
    return (0 <= n1 < n) and (0 <= n2 < n)

def main() -> None:
    n = get_valid_input("Enter the number of elements: ", 1, float('inf'))

    matrix = [[0] * n for _ in range(n)]

    populate_matrix(matrix, n)

    src = get_valid_input("\nEnter the source node: ", 0, n - 1)
    dst = get_valid_input("Enter the destination node: ", 0, n - 1)

    bfs(matrix, n, src, dst)

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
0 1 2 3 4 
Destination found!
'''