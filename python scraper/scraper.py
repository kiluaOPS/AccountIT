import requests
import pandas
from bs4 import BeautifulSoup
import time
import sys
import os
from datetime import date
from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.webdriver.firefox.firefox_binary import FirefoxBinary


def readAndOutput(html, value, dirName):
    soup=BeautifulSoup(html, "lxml")
    table=soup.find_all("span",{"class":"forecast-monthly__calendar"})
    l=[]
    for items in table:
      # print(items)
      for i in range(len(items.find_all("div", {"class":"dayCell"}))-1):
        d = {}
        d["date"]=items.find_all("div",{"class":"date"})[i].text
        d["hi-temp"]=items.find_all("div",{"class":"temp hi"})[i].text
        d["low-temp"]=items.find_all("div",{"class":"temp low"})[i].text
        l.append(d)

    df = pandas.DataFrame(l)
    if not os.path.exists(dirName):
        os.mkdir(dirName)
        print("Directory " , dirName ,  " Created ")
    else:
        print("Directory " , dirName ,  " already exists")
    df.to_csv(dirName + "//" + value + ".csv")



def main():
    arguments = []
    for arg in sys.argv[1:]:
        arguments.append(arg)

    url = "https://weather.com/weather/monthly/l/d9c3fa2c79668c489b8481d623991bdda4253177a2789d05a13dde82eadac46a"
    # binary = FirefoxBinary(r'C://Program Files//Mozilla Firefox//firefox.exe')
    # driver = webdriver.Firefox(firefox_binary=binary)
    driver = webdriver.PhantomJS('python scraper//phantomjs-2.1.1-windows//bin//phantomjs.exe')

    driver.get(url)

    # driver = webdriver.Firefox(firefox_binary=binary)



    value = 0
    values = []
    elem = driver.find_element_by_tag_name('body')
    for option in elem.find_elements_by_tag_name('option'):
            print(option.get_attribute('value'))
            values.append(option.get_attribute('value'))

    dates = []
    # here I get at which month we are and stort crawlong at that month
    for d in values:
        print(d)
        split = d.split("-")
        t1 = date(year = int(split[0]), month = int(split[1]), day = int(split[2]))
        dates.append(t1)

    today = date.today();
    max = 0
    for dat in dates:
        max +=1
        if dat > today:
            break
    max -=1

    for i in range(max):
        elem = driver.find_element_by_tag_name('body')
        options = elem.find_elements_by_tag_name('option')
        v = options[value].get_attribute('value');
        value += 1
        sel= Select(driver.find_element_by_id('month-picker'))

        sel.select_by_value(v)

        time.sleep(10)
        driver.save_screenshot( arguments[0] + "//screen"+ values[i] +'.png')
        html = driver.page_source
        readAndOutput(html, values[i]  , arguments[0]);




if __name__ == "__main__": main()
