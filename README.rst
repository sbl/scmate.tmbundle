====================================
A TextMate Bundle for SuperCollider
====================================


Work is based on Rob W's `supercollider-tmbundle`_. This implementation uses pipes for communication instead of OSC messages. This means you don't have to start the supercollider cocoa app to use the bundle and you don't have a ~300 character limit to send messages to SC.

There is a short preview `on vimeo <http://vimeo.com/12853571>`__.

.. raw:: html

  <iframe src="http://player.vimeo.com/video/12853571?title=0&amp;byline=0&amp;portrait=0" width="400" height="300" frameborder="0"></iframe><p><a href="http://vimeo.com/12853571">SuperCollider Textmate Bundle</a> from <a href="http://vimeo.com/sbl">sbl</a> on <a href="http://vimeo.com">Vimeo</a>.</p>

Prerequisites
=============

* TextMate Terminal executable ``mate`` (Help/Terminal Usage...)
* A SuperCollider Installation (>3.5) installed under ``/Applications/SuperCollider.app`` (you'll need to manually update the paths under scmate.tmbundle/Support/scpipe/lib/sc/pipe.rb if you need another installation directory)
* For a GUI it is recommended to use the new QT GUI (Cocoa won't work). Add ``GUI.qt;`` to your ``startup.scd``

Features
========

* Snippets
* Help is displayed via the new SC Help System
* Common commands (Recompile, Open Class File, Find References ...)

Installation
============

* ``git clone http://github.com/sbl/scmate.tmbundle.git ~/Library/Application\ Support/TextMate/Bundles/scmate.tmbundle``
* (OR) download `the bundle`_ and double-click it
* choose "finish installation" from the Bundle Menu
* Start the language with ``cmd+f1`` (a terminal window will come up)

Troubleshooting
===============

See `the announcement post`_ for this package for help with some common troubles:

Licence
=======

This work is licensed under the `GNU GPL`_ 
sbl 2010

.. _the announcement post: http://new-supercollider-mailing-lists-forums-use-these.2681727.n2.nabble.com/scmate-textmate-bundle-td5239359.html
.. _GNU GPL: http://www.gnu.org/licenses/gpl.html
.. _the bundle: http://github.com/downloads/sbl/scmate.tmbundle/scmate.tmbundle.zip
.. _supercollider-tmbundle: http://github.com/rfwatson/supercollider-tmbundle
