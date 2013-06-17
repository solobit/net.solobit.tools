(ns clojure.whistle.nomenclature
  (:use [clojure.pprint]
        [opennlp.nlp]
        [opennlp.treebank]))

(defn symbol-list-draft
  "An ideogram or ideograph ἰδέα + γράφω is a graphic symbol that represents an idea or concept."
  []
  )

(make-tree (first (treebank-parser ["This is a sentence ."])))

(pmap #(int %)
      (vals {:leader \⪮
             :entity \〠
             :thread \ຮ
             :option \〇
             :signal \↯
             :traffs \〶
             :target \㉧
             :marked \✪
             :undone \⟲
             :repeat \⟳
             :looped \➰
             :bridge \⟚
             :writes \✒
             :checks \✔
             :failed \✘
             :flanks \ミ
             :defend \㋯
             :offend \⟴
             :lguard \⦕
             :rguard \⦖
             :movfwd \⟰
             :movbck \⟱
             :format {:closin \⁙ :single \⁚ :spread \⁛}
             :cbloop \⥁
             :follow \⤹
             :weaken \〄
             :electr \㇋
             :downst \〽
             :square \⿻
             :weapon {:light \‵ :medium \‶ :heavy \‷}
             :deceit \‽
             :meteor \⁂
             :parkin \℗
             :attack \⎀
             :helpme \ℍ
             :netraf \↹
             :infini \∞
             :hooked \∾
             :inject \⇻
             :findit "🔍"
             :mobile "📱"
             :change \∆
             :paylds \∇
             :owners '(\∈ \∉ \∊ \∋ \∌ \∍)
             :obstcl \∎
             :indoor \∏
             :noammo \∐
             :supprt \∓
             :pointr \∘
             :search {:left \≲ :right \≳}
             :danger {:left \⋘ :right \⋙}
             :guards {:left \⋐ :right \⋑ :front \⋒ :back \⋓}
             :explos \⌓
             :totals \⅀
             :timers \⏳
             :bombed \⍾
             :coding \⌘
             :player {:fwd \⏩ :rev \⏪ :inj \⏫ :out \⏬ :ffwd \⏭ :frev \⏮ :play \⏯}
             :fatals \☠
             :window {:top-left \◰ :bottom-left \◱ :bottom-right \◲ :top-right \◳}
             :surrnd (map int '(\□ \▣ \■ \▤ \▦ \▥ \▧ \▩ \▨ \░ \▒ \▓))
             :logins {:out \⍇ :in \⍈}
             :satcom {:phone \☎}
             :detect \ↂ
             :alarms \⎃
             :advatt \⎂
             :advpat \⎁
             :gather \◌
             :breaks \☕
             :goals1 \⚑
             :goals2 \⚐
             :medics \☡
             :combat \⚔
             :warnin \⚠
             :unlock \⚷
             :denial \⛆
             :locked \⚿
             :clouds \⛅
             :jammin \⛈
             :curren \⚡
             :doctor \☤
             :emiter \☄
             :nuclea \☢
             :repair \⛍
             :broadb \⛙
             :chains \⛓
             :redirs \⛕
             :cutout \✀
             :nation \⛿
             :emails \✉
             :noting \✎
             :campin \⛺
             :losing \⛐
             :maints \⛑
             :traces \⛶
             :antivs \⛨
             :airlin \✈
             :shield \⛻
             :finals \⛳
             :trusts \✌
             :ipsock \⚼
             :transp \⛟
             :signer \✍
             :focusd \⛢
             :airdef \⛼
             :fuelpt \⛽
             :cabels \⚲
             :blocks \✋
             :blockr \⛔
             :magazi \⛫
             :anchor \⚯
             :javaee \⛾
             :mechan \⚙
             :booled {:yes \⚪ :no \⚫}
             :remark \✏
             :safety {:secure \⛉ :exposed \⛊}
             :hitraf \⛖
             :atomic \⚛
             :quarts {:first \◷ :second \◶ :third \◵ :last \◴ }
             :biohaz \☣
             :stores {:vdisk \⛀ :vcluster \⛁ :pdisk \⛂ :pcluster \⛃}
             :fcover {:front \⏜ :back \⏝}
             :unknwn \⁇
             :allied \℀
             :harden {:armor \∮ :extra \∯ :super \∰}
             :expand \⊞
             :folded \⊟}))

(def directions [\⬉\⬆\⬈
                 \⬅\⬌\➡\⬍
                 \⬋\⬇\⬊  ])

(map char (map int directions))

;; Use (zipmap ...) when you want to directly construct a hashmap from separate
;; sequences of keys and values. The output is a hashmap:

(def walls
  (zipmap (reverse (map keyword (map str (map char (range 97 106)))))
          (reverse (list \┌\┬\┐
                         \├\┼\┤
                         \└\┴\┘))))
(:c walls)


(map int '(\╴ \─ \╌ \┄ \┈ \╶ \╾ \╸ \━ \╍ \┅ \┉ \╺ \╼ \╵ \│ \╎ \┆ \┊ \╷ \╿ \╹ \┃ \╏ \┇ \┋ \╻ \╽))


(int ((:t walls) :l))

(doall (pmap int walls2))


