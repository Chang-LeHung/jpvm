string = "Hello, World!"

length = len(string)
print("Length:", length)

uppercase = string.upper()
lowercase = string.lower()
print("Uppercase:", uppercase)
print("Lowercase:", lowercase)

startswith = string.startswith("Hello")
endswith = string.endswith("World!")
print("Starts with 'Hello':", startswith)
print("Ends with 'World!':", endswith)

index = string.index("World")
count = string.count("o")
print("Index of 'World':", index)
print("Count of 'o':", count)

split = string.split(", ")
print("Split result:", split)

join = ", ".join(split)
print("Join result:", join)

replace = string.replace("World", "Python")
print("Replace result:", replace)

strip = "   Hello, World!   "
stripped = strip.strip()
print("Stripped:", stripped)

slice = string[7:12]
print("Slice:", slice)

numeric = string.isnumeric()
print("Is numeric:", numeric)

alpha = string.isalpha()
print("Is alphabetic:", alpha)
