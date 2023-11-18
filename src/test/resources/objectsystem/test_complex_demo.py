import time


class PluginMeta(type):

    def __new__(cls, name, bases, dct):
        print("In PluginMeta")
        # if name != 'BasePlugin' and 'process' not in dct:
        #     raise TypeError('subclass {} should implement process method'.format(name))
        return super().__new__(cls, name, bases, dct)


class BasePlugin(metaclass=PluginMeta):

    def __init__(self, interval):
        self.interval = interval

    def serve_forever(self):
        while True:
            self.process()
            time.sleep(self.interval)


class BarPlugin(BasePlugin):

    def process(self):
        print('bar processing')


bar = BarPlugin(interval=5)


class FooPlugin(BasePlugin):

    def process(self):
        print('foo processing')


foo = FooPlugin(interval=5)


class FaultPlugin(BasePlugin):

    def run(self):
        print('xxxx')


fault = FaultPlugin(interval=5)
