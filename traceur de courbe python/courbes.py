import csv
import matplotlib.pyplot as plt

# Strong scaling
p = []
medianT = []

with open("pi_strong_scaling.csv") as f:
    next(f)
    for row in csv.reader(f):
        p.append(int(row[0]))
        medianT.append(float(row[2]))

T1 = medianT[0]
speedup = [T1 / t for t in medianT]

plt.plot(p, speedup, marker='o', label="Speedup mesuré")
plt.plot(p, p, "--", label="Speedup optimal")
plt.xlabel("p")
plt.ylabel("Speedup")
plt.legend()
plt.show()

# Weak scaling
p2 = []
Tweak = []

with open("pi_weak_scaling.csv") as f:
    next(f)
    for row in csv.reader(f):
        p2.append(int(row[0]))
        Tweak.append(float(row[2]))

T1w = Tweak[0]
eff = [T1w / t for t in Tweak]

plt.plot(p2, eff, marker='o', label="Efficacité mesurée")
plt.plot(p2, [1]*len(p2), "--", label="Efficacité idéale")
plt.xlabel("p")
plt.ylabel("Efficacité")
plt.legend()
plt.show()
