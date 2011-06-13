A TextMate Bundle for SuperCollider
###################################

Work is based on http://github.com/rfwatson/supercollider-tmbundle by Rob W. This implementation uses pipes for communication instead of OSC messages. This means you don't have to start the supercollider cocoa app to use the bundle and you don't have a ~300 character limit to send messages to SC.

Short preview: http://vimeo.com/12853571

Prerequisites:
* TextMate Terminal executable "mate" (Help/Terminal Usage...)
* A recent SuperCollider Installation (>3.3) installed under /Applications/SuperCollider
* If you want GUI display you have to install the SwingOSC.jar in the root (/Applications/SuperCollider) folder, which is the default anyways.

Features:
* Snippets
* Uses the native TextMate Browser to display the help
* Common commands (Recompile, Open Class File, Find References ...)

Installation:
* git clone http://github.com/sbl/scmate.tmbundle.git ~/Library/Application\ Support/TextMate/Bundles/scmate.tmbundle
* (OR) download the bundle http://github.com/downloads/sbl/scmate.tmbundle/scmate.tmbundle.zip and double-click it
* choose "finish installation" from the Bundle Menu
* Start the language with cmd+f1 (a terminal window will come up)

This work is licensed under the GNU GPL http://www.gnu.org/licenses/gpl.html
sbl 2010