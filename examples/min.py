import random

SIZE = 10


def ingenuity_min(array):
    comparisons = 0

    for i in array:
        isMin = 1

        for j in array:
            comparisons += 1

            if j < i:
                isMin = 0

        if isMin:
            print("Comparisons:", comparisons)
            return i


def best_min(array):
    comparisons = 0

    minimum = array[0]

    for i in range(1, len(array)):
        comparisons += 1

        if array[i] < minimum:
            minimum = array[i]

    print("Comparisons:", comparisons)
    return minimum


s = []

for i in range(SIZE):
    s.append(random.randint(1, 100))

print(s)

print("Ingenuity min:", ingenuity_min(s))
print("Best min:", best_min(s))