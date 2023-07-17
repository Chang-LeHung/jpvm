import threading
import time


def thread_job():
    print(threading.current_thread())
    time.sleep(0.1)


def main():
    added_threads = []
    for i in range(5):
        thread = threading.Thread(target=thread_job)
        added_threads.append(thread)
        thread.start()

    for thread in added_threads:
        thread.join()


if __name__ == '__main__':
    main()
    t = threading.current_thread()
    print(t)
