import url
from url import urlopen, urlretrieve, urlparse

ur = urlopen("https://www.baidu.com")
print(ur)

# urlretrieve("https://www.baidu.com", "src/test/resources/obsy/test.txt");


o = urlparse("https://example.com:8080/path/to/page?query=java&category=tutorial#section1")
print(o)
# print(o.port)
# print(o.protocol)
# print(o.host)
# print(o.path)
# print(o.query)
# print(o.fragment)
