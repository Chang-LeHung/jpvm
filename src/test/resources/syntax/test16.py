person = {"name": "John", "age": 30, "city": "New York"}

for (kev, val) in person.items():
    print(kev, val)

for val in person.values():
    print(f"{val = }")

for key in person.keys():
    print(f"{key = }")

print(f"{person['name'] = }")

person['hello'] = "world"
print(person)
