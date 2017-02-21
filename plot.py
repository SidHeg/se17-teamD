#cleaning user survey data
from pyexcel_ods import get_data
import matplotlib.pyplot as plt
from math import floor
import numpy as np
import string, re

data_ordered_dic = get_data("user_survey.ods")
data = data_ordered_dic.popitem()
data = data[1]
table1 = list()
table2 = {"yes": 0, "no": 0}
table3 = dict()
for line in data[1:]:
	#data for the 1st figure
	try:
		if ((isinstance(line[1], int) | isinstance(line[1], float)) & (line[1] > 1)):
			line[1] = float(line[1])/100
		line[1] = line[1]*100
		table1.append(int(round(line[1])))
	except:
		pass
	#data for the 2nd figure
	try:
		if(line[2] == "yes"):
			table2["yes"] = table2["yes"] + 1
		if(line[2] == "no"):
			table2["no"] = table2["no"] + 1
	except:
		pass
	#data for the 3rd figure
	"""try:
		match = re.search('(\d*\.*\d*)\,+\s+(\d*\.*\d*)', line[3])
		m = re.search('(\d*\.*\d*)', line[3])
		if match:
			if not (table3.has_key(int(match.group(2)))):
				table3[int(match.group(2))] = list()
			table3[int(match.group(2))].append(float(match.group(1)))
		else:
			print(type(line[3]))
			if m:
				if not (table3.has_key("NA")):
					table3["NA"] = list()
				table3["NA"].append(float(m.group(0)))"""
	
x = list(xrange(1, len(table1)+1))
m1 = np.mean(table1)
print(table3)

print(m1, x[-1])
#figure 1
plt.figure(1)
font = {'family' : 'normal',
        'size'   : 16}
plt.scatter(x, table1, marker='o')
plt.plot([x[0], x[-1]], [m1, m1], 'k-', color = "r")
plt.title("What percentage of your teachers/professors have\n taken attendance in school and college?", font)
plt.xlabel("Respondants")
plt.ylabel("Percentage")
plt.yticks(np.arange(0, 101, 10))
plt.xticks(np.arange(0, 51, 10))
plt.text(25, m1+2, "mean = {0}".format(round((m1), 2)))

print(table2)
#figure 2
plt.figure(2, figsize=(6,6))
plt.title("plot2")
label = "yes", "no"
plt.pie([table2["yes"], table2["no"]], labels=label)

#figure 3
plt.show()
