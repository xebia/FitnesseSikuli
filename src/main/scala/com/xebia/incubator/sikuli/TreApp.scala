package com.xebia.incubator.sikuli
import org.sikuli.script._
import org.slf4j._
import java.util.prefs.Preferences
import com.xebia.tre.domain.Item
class TreApp(prefs:Preferences) {
    val log:Logger = LoggerFactory.getLogger(getClass())
    
    var searchButton: Match = null
    //Scan 10x per seconds for changes on observed areas
    Settings.ObserveScanRate = 10
    //Move and click in 0.1 seconds, so we can refresh 10x per second
    Settings.MoveMouseDelay = 0.1F

    var sikuliDisabled = prefs.getBoolean("sikuliDisabled", false)
    def treAppWindow: Option[Region] = {
        //Must open Screen object, else Sikuli calls might hang
        val screen: Screen = new Screen
        val tre = App.focus("The Receivables Exchange");
        if (tre == null)
            None;
        else {
            log.info ("Found TRE application: " + tre)
            val win = tre.window()
            log.info("TRE Window activated: " + win)
            Option(win);
        }
    }
    def highlightSearchButton() {
        if (!sikuliDisabled) {
            val treWin = treAppWindow.getOrElse(throw new TreException("The Receivables Exchange is not active"))
            highlightSearchButton(treWin)
            treWin.find("Live Auctions") //For initializing the text engine
        }
    }

    private def highlightSearchButton(tre: Region) {

        if (searchButton == null) {
            log.info("Finding searchbutton");
            if (tre.exists("SearchLiveAuctions.sikuli/LlveAuctlons.png") == null) {
                tre.click("SearchLiveAuctions.sikuli/Auctlons.png")
                val searchMenu = tre.wait("SearchLiveAuctions.sikuli/SEBYCHLIVEAU.png")
                tre.click(searchMenu)
            }
            Thread.sleep(2000) //Wait for TRE window to active
            searchButton = tre.find("SearchLiveAuctions.sikuli/RWTH.png")
            enlargeRegion(searchButton,20)
            searchButton.highlight(2)
        }
    }
    
    def enlargeRegion(region: Region, size: Int) {
        region.setX(region.getX-size)
        region.setY(region.getY-size)
        region.setW(region.getW+size*2)
        region.setH(region.getH+size*2)
    }
    def refreshLiveAuctions {
        if (!sikuliDisabled) {
            val tre = treAppWindow.getOrElse(throw new TreException("The Receivables Exchange is not active"))
            if (searchButton == null) {
                highlightSearchButton(tre)
                log.info("Searchbutton highlighten");
            }
            tre.click(searchButton);
        }
    }

    def strip(sellerName: String) = {
        val p=sellerName.indexWhere(c => ",.".exists(_==c))
        if (p>=0)
            sellerName.substring(0, p)
        else
            sellerName
    }
    def openAuction(auctionId:Long) {
        openAuction(auctionId.toString)
    }
    def openAuction(auctionId:String) {
        if (!sikuliDisabled) {
            val treWin = treAppWindow.getOrElse(throw new TreException("The Receivables Exchange is not active"))
            highlightSearchButton(treWin)
            treWin.click(searchButton)
            treWin.wait(0.05)
            searchButton.waitVanish("SearchLiveAuctions.sikuli/Search-wait.png",0.2)
            val it=treWin.findAll(auctionId)
            var highestMatch:Match=null
            var resultSize=0
            while (it.hasNext) {
                val m=it.next
                log.debug("Match gevonden met score {}",m.getScore)
                if (highestMatch==null || m.getScore>highestMatch.getScore) {
                    highestMatch=m;
                }
                resultSize=resultSize+1
            }
            if (highestMatch!=null) {
                val foundAuctionId=highestMatch.text.replaceAll("I","1");
                log.debug("Found click target with text: "+highestMatch.text)
                if (foundAuctionId.equals(auctionId))
                    highestMatch.click(highestMatch.getTarget)
                else
                    highestMatch.hover(highestMatch.getTarget)
            } else
                log.warn("No match found for {}",auctionId)

        }
    }

    def disableAutomation {
        sikuliDisabled = true;
    }

    /*For testing locally*/
    def main(args: Array[String]) {
        println("Testing sikuli calls")
//        highlightSearchButton
//        refreshLiveAuctions
//        Thread.sleep(5000)
//        refreshLiveAuctions
//        Thread.sleep(2000)
        openAuction(17967)

    }
}


