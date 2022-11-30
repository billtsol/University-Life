
import math


class Breath():
    def __init__(self, startPoint, endPoint, maxTreeHeight):
        self.maxTreeHeight = maxTreeHeight
        self.startPoint = startPoint
        self.endPoint = endPoint
        self.queue = [startPoint]
        self.haveChecked = []
        self.currentPossition = 0
        self.solutionTalbe = []
        self.childrens = []

        self.counter = 1

    def addInHaveChecked(self, checked):
        self.haveChecked.append(checked)

    def addNewChildrens(self, newChildrens):
        self.childrens = newChildrens

    def addInQueue(self, moreInQueue):
        self.queue.extend(moreInQueue)

    def removeFromQueue(self):
        return self.queue.pop(0)

    def search(self):

        if (self.maxTreeHeight == 0):
            return 0
        self.maxTreeHeight -= 1

        self.currentPossition = self.removeFromQueue()

        if self.currentPossition in self.haveChecked:
            # return 0
            self.search()
        elif self.currentPossition == self.endPoint:
            self.addInHaveChecked(self.currentPossition)
            print("FInd")
            return 1
        else:

            self.addInHaveChecked(self.currentPossition)
            newChildrens = []
            print(self.currentPossition)
            if isinstance(self.currentPossition, float):
                newChildrens.append(int(self.currentPossition))
                newChildrens.append(math.sqrt(self.currentPossition))
            else:
                if self.currentPossition < 30:
                    newChildrens.append(math.factorial(self.currentPossition))
                newChildrens.append(math.sqrt(self.currentPossition))

            self.solutionTalbe.append({
                "previousInList": len(self.solutionTalbe) - 1,
                "prev": self.currentPossition,
                "current": newChildrens[0],
            })

            if len(newChildrens) == 2:
                # for i in range(len(self.solutionTalbe)):
                # if
                self.solutionTalbe.append({
                    "previousInList": len(self.solutionTalbe) - 1,
                    "prev": self.currentPossition,
                    "current": newChildrens[1]
                })

            self.addNewChildrens(newChildrens)
            self.addInQueue(self.childrens)
            self.search()


test = Breath(4, 5, 60)

print(test.search())
print(test.solutionTalbe)

for i in range(len(test.solutionTalbe)):
    print(test.solutionTalbe[i])

# s1 = math.factorial(4)
# print(s1)

# s1 = math.factorial(s1)
# print(s1)

# s1 = math.sqrt(s1)
# print(s1)

# s1 = math.sqrt(s1)
# print(s1)

# s1 = math.sqrt(s1)
# print(s1)

# s1 = math.sqrt(s1)
# print(s1)

# s1 = math.sqrt(s1)
# print(s1)

# s1 = int(s1)
# print(s1)
