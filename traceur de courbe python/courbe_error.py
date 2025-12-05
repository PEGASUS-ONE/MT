import csv
import matplotlib.pyplot as plt
import math

N = []
logerr = []

with open("pi_errors_log10.csv") as f:
    next(f)
    for row in csv.reader(f):
        N.append(int(row[0]))
        logerr.append(float(row[2]))

plt.plot(N, logerr, marker='o')
plt.xscale("log")
plt.xlabel("N total (log)")
plt.ylabel("log10(erreur relative)")
plt.title("Convergence Monte Carlo de π")
plt.grid(True)
plt.show()
