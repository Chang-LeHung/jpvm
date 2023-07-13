import request
from request import urlopen, title
ur = urlopen("http://www.baidu.com")
print(ur.title())